package com.wilfredo.courier_logistics_api.domain.model;

import com.wilfredo.courier_logistics_api.domain.enums.PackageStatus;
import com.wilfredo.courier_logistics_api.domain.enums.ProductCategory;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @Version
    private Long version;

    @Column(unique = true, nullable = false, length =  30)
     private String trackingNumber;

    @Column(nullable = false)
    private String description;

    @Column(precision = 10, scale = 3, nullable = false)
    private BigDecimal weightInLbs;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal fobValue;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal freightCost;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal customsDuty;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal selectiveTax;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal itbis;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal totalCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackageStatus currentStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("timestamp ASC")
    private List<TrackingEvent> events = new ArrayList<>();


    protected Shipment() {
    }

    public Shipment(
            String trackingNumber,
            String description,
            BigDecimal weightInLbs,
            BigDecimal fobValue,
            BigDecimal freightCost,
            BigDecimal customsDuty,
            BigDecimal selectiveTax,
            BigDecimal itbis,
            BigDecimal totalCost,
            ProductCategory category,
            PackageStatus currentStatus) {
        this.trackingNumber = trackingNumber;
        this.description = description;
        this.weightInLbs = weightInLbs;
        this.fobValue = fobValue;
        this.freightCost = freightCost;
        this.customsDuty = customsDuty;
        this.selectiveTax = selectiveTax;
        this.itbis = itbis;
        this.totalCost = totalCost;
        this.category = category;
        this.currentStatus = currentStatus;
        this.createdAt = LocalDateTime.now();
    }

    public void addEvent(TrackingEvent event) {
        this.events.add(event);
        event.setShipment(this);
    }
    public void setCurrentStatus(PackageStatus currentStatus){
        if (currentStatus == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.currentStatus = currentStatus;
    }


    public Long getId() {
        return id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getWeightInLbs() {
        return weightInLbs;
    }

    public BigDecimal getFobValue() {
        return fobValue;
    }

    public BigDecimal getFreightCost() {
        return freightCost;
    }

    public BigDecimal getCustomsDuty() {
        return customsDuty;
    }

    public BigDecimal getSelectiveTax() {
        return selectiveTax;
    }

    public BigDecimal getItbis() {
        return itbis;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public PackageStatus getCurrentStatus() {
        return currentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<TrackingEvent> getEvents() {
        return Collections.unmodifiableList(events);
    }
}

