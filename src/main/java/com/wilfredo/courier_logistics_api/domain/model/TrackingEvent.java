package com.wilfredo.courier_logistics_api.domain.model;

import com.wilfredo.courier_logistics_api.domain.enums.PackageStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tracking_events")
public class TrackingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackageStatus status;


    @Column(nullable = false, length = 120)
    private String location;


    @Column(length = 255)
    private String notes;

    @Column(nullable = false,
    updatable = false)
   private LocalDateTime timestamp;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;


    protected TrackingEvent() {}

    public TrackingEvent(PackageStatus status, String location, String notes) {
       this.timestamp = LocalDateTime.now();
       this.status = status;
       this.location = location;
       this.notes = notes;
    }

void setShipment(Shipment shipment){
        this.shipment = shipment;
}

    public Shipment getShipment() {
        return shipment;
    }

    public Long getId() {
        return id;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getNotes() {
        return notes;
    }

    public String getLocation() {
        return location;
    }
}
