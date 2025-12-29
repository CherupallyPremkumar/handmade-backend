package com.handmade.ecommerce.shipping.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import org.chenile.utils.entity.model.ChenileEntity;
import java.time.LocalDateTime;

/**
 * Shipment - Aggregate Root
 * Represents a shipment for an order
 */
@Entity
@Table(name = "shipments", indexes = {
    @Index(name = "idx_order_id", columnList = "order_id"),
    @Index(name = "idx_tracking_number", columnList = "tracking_number"),
    @Index(name = "idx_status", columnList = "status")
})
public class Shipment extends BaseJpaEntity {
    

    
    @Column(name = "order_id", nullable = false, length = 50)
    private String orderId; // Reference to Order Management
    
    @Column(name = "tracking_number", unique = true, length = 100)
    private String trackingNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "carrier", length = 20)
    private Carrier carrier;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ShipmentStatus status;
    
    @Embedded
    private TrackingInfo trackingInfo;
    
    @Column(name = "shipping_label_url", length = 500)
    private String shippingLabelUrl;
    
    @Column(name = "estimated_delivery_date")
    private LocalDateTime estimatedDeliveryDate;
    
    @Column(name = "actual_delivery_date")
    private LocalDateTime actualDeliveryDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;
    
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    
    // Getters and Setters

    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public Carrier getCarrier() {
        return carrier;
    }
    
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }
    
    public ShipmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }
    
    public TrackingInfo getTrackingInfo() {
        return trackingInfo;
    }
    
    public void setTrackingInfo(TrackingInfo trackingInfo) {
        this.trackingInfo = trackingInfo;
    }
    
    public String getShippingLabelUrl() {
        return shippingLabelUrl;
    }
    
    public void setShippingLabelUrl(String shippingLabelUrl) {
        this.shippingLabelUrl = shippingLabelUrl;
    }
    
    public LocalDateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }
    
    public void setEstimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }
    
    public LocalDateTime getActualDeliveryDate() {
        return actualDeliveryDate;
    }
    
    public void setActualDeliveryDate(LocalDateTime actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getShippedAt() {
        return shippedAt;
    }
    
    public void setShippedAt(LocalDateTime shippedAt) {
        this.shippedAt = shippedAt;
    }
    
    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }
    
    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }
    

    
    /**
     * Mark shipment as shipped
     */
    public void ship(String trackingNumber) {
        this.status = ShipmentStatus.IN_TRANSIT;
        this.trackingNumber = trackingNumber;
        this.shippedAt = LocalDateTime.now();
    }
    
    /**
     * Mark shipment as delivered
     */
    public void deliver() {
        this.status = ShipmentStatus.DELIVERED;
        this.deliveredAt = LocalDateTime.now();
        this.actualDeliveryDate = LocalDateTime.now();
    }
}
