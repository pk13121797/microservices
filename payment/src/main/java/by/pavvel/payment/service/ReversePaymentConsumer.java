package by.pavvel.payment.service;

import by.pavvel.payment.model.*;
import by.pavvel.payment.repository.PaymentRepository;
import com.example.amqp.RabbitMQMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ReversePaymentConsumer {

    private final PaymentRepository paymentRepository;

    private final RabbitMQMessageProducer messageProducer;

    public ReversePaymentConsumer(PaymentRepository paymentRepository, RabbitMQMessageProducer messageProducer) {
        this.paymentRepository = paymentRepository;
        this.messageProducer = messageProducer;
    }

    @KafkaListener(
            topics = "reversed-payments",
            groupId = "payments-group",
            containerFactory = "paymentFactory"
    )
    public void reversePayment(PaymentEvent paymentEvent) {
        try {
            log.info("payment reverse {}", paymentEvent);
            Optional<Payment> paymentById = paymentRepository.findById(paymentEvent.getOrderRequest().getId());
            log.info("payment by id{}", paymentById);
            paymentById.ifPresent(p -> {
                p.setPaymentStatus(PaymentStatus.CANCELLED);
                paymentRepository.save(p);

                // reverse order task
                OrderEvent orderEvent = new OrderEvent();
                orderEvent.setOrderRequest(paymentEvent.getOrderRequest());
                orderEvent.setOrderStatus(OrderStatus.CANCELLED);

                messageProducer.publish(
                        orderEvent,
                        "internal.exchange",
                        "internal.reverse-order.routing-key"
                );

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
