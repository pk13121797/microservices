package by.pavvel.order.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private Long id;

    private String title;

    private Integer quantity;

    private Integer customerId;

    private String customerEmail;

    private String address;

    public OrderRequest() {
    }

    public OrderRequest(String title, Integer quantity, Integer customerId, String customerEmail, String address) {
        this.title = title;
        this.quantity = quantity;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", quantity=" + quantity +
                ", customerId=" + customerId +
                ", customerEmail='" + customerEmail + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
