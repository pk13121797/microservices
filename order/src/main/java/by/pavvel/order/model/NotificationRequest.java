package by.pavvel.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {

    private Integer customerId;

    private String customerEmail;

    private String message;

    public NotificationRequest() {
    }

    public NotificationRequest(Integer customerId, String customerEmail, String message) {
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.message = message;
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "customerId=" + customerId +
                ", customerEmail='" + customerEmail + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
