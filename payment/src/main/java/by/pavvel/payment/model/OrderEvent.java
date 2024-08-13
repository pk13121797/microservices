package by.pavvel.payment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEvent {

    private OrderStatus orderStatus;

    private OrderRequest orderRequest;

    public OrderEvent() {
    }

    public OrderEvent(OrderStatus orderStatus, OrderRequest orderRequest) {
        this.orderStatus = orderStatus;
        this.orderRequest = orderRequest;
    }
}
