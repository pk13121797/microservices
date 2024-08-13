package by.pavvel.shipment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Shipment")
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "address",nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private ShipmentStatus shipmentStatus;

    public Shipment() {
    }

    public Shipment(Long id, String address, ShipmentStatus shipmentStatus) {
        this.id = id;
        this.address = address;
        this.shipmentStatus = shipmentStatus;
    }
}
