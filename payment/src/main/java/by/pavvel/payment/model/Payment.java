package by.pavvel.payment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "Payment")
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private PaymentStatus paymentStatus;

    public Payment() {
    }

    public Payment(Long id, String title, PaymentStatus paymentStatus) {
        this.id = id;
        this.title = title;
        this.paymentStatus = paymentStatus;
    }
}
