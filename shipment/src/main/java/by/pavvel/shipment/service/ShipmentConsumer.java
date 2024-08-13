package by.pavvel.shipment.service;

import by.pavvel.payment.model.OrderRequest;
import by.pavvel.payment.model.OrderStatus;
import by.pavvel.payment.model.PaymentEvent;
import by.pavvel.shipment.model.Shipment;
import by.pavvel.shipment.model.ShipmentEvent;
import by.pavvel.shipment.model.ShipmentStatus;
import by.pavvel.shipment.repository.ShipmentRepository;
import com.example.clients.NotificationClient;
import com.example.clients.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShipmentConsumer {

    private final ShipmentRepository shipmentRepository;

    private final KafkaTemplate<String, PaymentEvent> kafkaPaymentTemplate;

    private final KafkaTemplate<String, ShipmentEvent> kafkaShipmentTemplate;

    private final NotificationClient notificationClient;

    public ShipmentConsumer(ShipmentRepository shipmentRepository, KafkaTemplate<String, PaymentEvent> kafkaPaymentTemplate, KafkaTemplate<String, ShipmentEvent> kafkaShipmentTemplate, NotificationClient notificationClient) {
        this.shipmentRepository = shipmentRepository;
        this.kafkaPaymentTemplate = kafkaPaymentTemplate;
        this.kafkaShipmentTemplate = kafkaShipmentTemplate;
        this.notificationClient = notificationClient;
    }

    @KafkaListener(
            topics = "payments",
            groupId = "payments-group",
            containerFactory = "paymentFactory"
    )
    void listener(PaymentEvent paymentEvent) {

        log.info("shipment consumer {}", paymentEvent);

        OrderRequest orderRequest = paymentEvent.getOrderRequest();

        Shipment shipment = new Shipment();
        shipment.setAddress(orderRequest.getAddress());
        shipment.setShipmentStatus(ShipmentStatus.DELIVERED);
        shipmentRepository.save(shipment);

        try {
            if (orderRequest.getAddress().equals("minsk")) {
                compensateShipment(paymentEvent, shipment, orderRequest);
            } else {

                ShipmentEvent shipmentEvent = new ShipmentEvent();
                shipmentEvent.setOrderRequest(orderRequest);
                shipmentEvent.setOrderStatus(OrderStatus.COMPLETED);
                kafkaShipmentTemplate.send("successful-order", shipmentEvent);

                NotificationRequest notificationRequest = new NotificationRequest(
                        orderRequest.getCustomerId(),
                        orderRequest.getCustomerEmail(),
                        "Successfully delivered.",
                        orderRequest.getAddress()
                );

                notificationClient.sendRestaurantNotification(notificationRequest);
            }
        } catch (RuntimeException e) {
            compensateShipment(paymentEvent, shipment, orderRequest);
        }
    }

    private void compensateShipment(PaymentEvent paymentEvent, Shipment shipment, OrderRequest orderRequest) {
        shipment.setAddress(orderRequest.getAddress());
        shipment.setShipmentStatus(ShipmentStatus.CANCELLED);
        shipmentRepository.save(shipment);

        // reverse payment task
        paymentEvent.setOrderRequest(orderRequest);
        kafkaPaymentTemplate.send("reversed-payments", paymentEvent);
    }
}
