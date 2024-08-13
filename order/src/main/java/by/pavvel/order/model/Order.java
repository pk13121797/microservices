package by.pavvel.order.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "Order")
@Table(name = "order_info")
public class Order {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "creation_date",nullable = false)
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(Long id, String title, Integer quantity, LocalDate creationDate, OrderStatus orderStatus) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.creationDate = creationDate;
        this.orderStatus = orderStatus;
    }
}
