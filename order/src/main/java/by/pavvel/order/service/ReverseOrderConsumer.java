package by.pavvel.order.service;

import by.pavvel.order.model.Order;
import by.pavvel.order.model.OrderEvent;
import by.pavvel.order.model.OrderStatus;
import by.pavvel.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ReverseOrderConsumer {

    private final OrderRepository orderRepository;

    public ReverseOrderConsumer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "${rabbitmq.queues.reverse-order}")
    public void reverseOrder(OrderEvent orderEvent) {
        log.info("order reverse {}", orderEvent);
        try {
            Optional<Order> orderById = orderRepository.findById(orderEvent.getOrderRequest().getId());
            orderById.ifPresent(order -> {
                order.setOrderStatus(OrderStatus.CANCELLED);
                orderRepository.save(order);
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
