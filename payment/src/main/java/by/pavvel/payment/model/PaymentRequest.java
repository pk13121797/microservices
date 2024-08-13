package by.pavvel.payment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    private Long id;

    private String title;

    private Integer quantity;

    public PaymentRequest() {
    }

    public PaymentRequest(Long id, String title, Integer quantity) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
