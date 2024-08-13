package by.pavvel.notification.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {

    private Integer customerId;

    private String customerEmail;

    private String message;

    private String address;

    public NotificationRequest() {
    }

    public NotificationRequest(Integer customerId, String customerEmail, String message) {
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.message = message;
    }

    public NotificationRequest(Integer customerId, String customerEmail, String message, String address) {
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.message = message;
        this.address = address;
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "customerId=" + customerId +
                ", customerEmail='" + customerEmail + '\'' +
                ", message='" + message + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
