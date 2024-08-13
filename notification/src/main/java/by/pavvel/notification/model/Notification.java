package by.pavvel.notification.model;

import lombok.*;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "Notification")
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "customer_id",nullable = false)
    private Integer toCustomerId;

    @Column(name = "email",nullable = false)
    private String toCustomerEmail;

    @Column(name = "message",nullable = false)
    private String message;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "send_at",nullable = false)
    private LocalDateTime sendAt;

    public Notification() {
    }

    public Notification(Integer toCustomerId, String toCustomerEmail, String message, LocalDateTime sendAt) {
        this.toCustomerId = toCustomerId;
        this.toCustomerEmail = toCustomerEmail;
        this.message = message;
        this.sendAt = sendAt;
    }
}
