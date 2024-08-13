package by.pavvel.shipment.model;

import by.pavvel.payment.model.OrderRequest;
import by.pavvel.payment.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentEvent {

    private OrderStatus orderStatus;

    private OrderRequest orderRequest;

    public ShipmentEvent() {
    }

    public ShipmentEvent(OrderStatus orderStatus, OrderRequest orderRequest) {
        this.orderStatus = orderStatus;
        this.orderRequest = orderRequest;
    }
}
