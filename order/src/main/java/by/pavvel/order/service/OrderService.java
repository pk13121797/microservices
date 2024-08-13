package by.pavvel.order.service;

import by.pavvel.order.model.*;
import by.pavvel.order.repository.OrderRepository;
import by.pavvel.shipment.model.ShipmentEvent;
import com.example.amqp.RabbitMQMessageProducer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;

    private final RabbitMQMessageProducer messageProducer;

    public OrderService(OrderRepository orderRepository,
                        RestTemplate restTemplate,
                        RabbitMQMessageProducer messageProducer) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.messageProducer = messageProducer;
    }

    @CircuitBreaker(name = "orderService", fallbackMethod = "serviceFallback")
    public void createOrder(OrderRequest orderRequest) {

        Order order = new Order();

        try {

            order.setTitle(orderRequest.getTitle());
            order.setQuantity(orderRequest.getQuantity());
            order.setCreationDate(LocalDate.now());
            order.setOrderStatus(OrderStatus.CREATED);

            Order savedOrder = orderRepository.save(order);
            orderRequest.setId(savedOrder.getId());

            String orderNotificationMessage = """
                    Order %s created
                    """.formatted(orderRequest.getTitle());
            NotificationRequest notificationRequest = new NotificationRequest(
                    orderRequest.getCustomerId(),
                    orderRequest.getCustomerEmail(),
                    orderNotificationMessage
            );
            restTemplate.postForObject("http://NOTIFICATION/notifications", notificationRequest, NotificationRequest.class);

            OrderEvent orderEvent = new OrderEvent();
            orderEvent.setOrderRequest(orderRequest);
            orderEvent.setOrderStatus(OrderStatus.CREATED);
            messageProducer.publish(
                    orderEvent,
                    "internal.exchange",
                    "internal.order.routing-key"
            );

        } catch (RuntimeException e) {
            compensateOrder(order);
        }
    }

    @KafkaListener(
            topics = "successful-order",
            groupId = "orders-group",
            containerFactory = "shipmentFactory"
    )
    public void successfullyMakeOrder(ShipmentEvent shipmentEvent) {
        by.pavvel.payment.model.OrderRequest orderRequest = shipmentEvent.getOrderRequest();
        log.info("order: {}", orderRequest);
        try {
            Optional<Order> orderById = orderRepository.findById(orderRequest.getId());
            orderById.ifPresent(order -> {
                order.setOrderStatus(OrderStatus.COMPLETED);
                orderRepository.save(order);

                String orderNotificationMessage = """
                    Order %s completed
                    """.formatted(orderRequest.getTitle());
                NotificationRequest notificationRequest = new NotificationRequest(
                        orderRequest.getCustomerId(),
                        orderRequest.getCustomerEmail(),
                        orderNotificationMessage
                );
                restTemplate.postForObject("http://NOTIFICATION/notifications", notificationRequest, NotificationRequest.class);
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void compensateOrder(Order order) {
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private void serviceFallback(OrderRequest orderRequest, Throwable t) {
        log.info("This is a service fallback method {} ", orderRequest);
    }
}
