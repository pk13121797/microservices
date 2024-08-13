package by.pavvel.payment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentEvent {

    private OrderStatus orderStatus;

    private OrderRequest orderRequest;

    public PaymentEvent() {
    }

    public PaymentEvent(OrderStatus orderStatus, OrderRequest orderRequest) {
        this.orderStatus = orderStatus;
        this.orderRequest = orderRequest;
    }
}
