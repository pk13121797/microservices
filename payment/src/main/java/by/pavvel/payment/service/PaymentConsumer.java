package by.pavvel.payment.service;

import by.pavvel.payment.model.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentConsumer {

    private final PaymentService paymentService;

    public PaymentConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = "${rabbitmq.queues.order}")
    public void consumer(OrderEvent orderEvent) {
        log.info("new payment request {}", orderEvent);
        paymentService.processPayment(orderEvent);
    }
}