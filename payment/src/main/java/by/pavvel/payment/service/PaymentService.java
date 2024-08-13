package by.pavvel.payment.service;

import by.pavvel.payment.model.*;
import by.pavvel.payment.repository.PaymentRepository;
import com.example.amqp.RabbitMQMessageProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final RabbitMQMessageProducer messageProducer;

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentService(PaymentRepository paymentRepository,
                          RabbitMQMessageProducer messageProducer,
                          KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.paymentRepository = paymentRepository;
        this.messageProducer = messageProducer;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void processPayment(OrderEvent orderEvent) {

        OrderRequest orderRequest = orderEvent.getOrderRequest();
        Payment payment = new Payment();

        payment.setTitle(orderRequest.getTitle());
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        Payment savedPayment = paymentRepository.save(payment);
        orderRequest.setId(savedPayment.getId());

        try {
            if (orderRequest.getQuantity() == 10) {
                compensatePayment(orderEvent, payment, orderRequest);
            } else {
                PaymentEvent paymentEvent = new PaymentEvent();
                paymentEvent.setOrderRequest(orderRequest);
                paymentEvent.setOrderStatus(OrderStatus.PAYED);

                kafkaTemplate.send("payments", paymentEvent);
            }
        } catch (RuntimeException e) {
            compensatePayment(orderEvent, payment, orderRequest);
        }

    }

    private void compensatePayment(OrderEvent orderEvent, Payment payment, OrderRequest orderRequest) {
        payment.setTitle(orderRequest.getTitle());
        payment.setPaymentStatus(PaymentStatus.CANCELLED);
        Payment savedPayment = paymentRepository.save(payment);
        orderRequest.setId(savedPayment.getId());

        // reverse order task
        messageProducer.publish(
                orderEvent,
                "internal.exchange",
                "internal.reverse-order.routing-key"
        );
    }
}
