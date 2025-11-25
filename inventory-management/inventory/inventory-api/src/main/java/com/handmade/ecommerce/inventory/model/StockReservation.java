package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * StockReservation - Entity
 * Tracks stock reserved for pending orders
 */
@Entity
@Table(name = "stock_reservations", indexes = {
        @Index(name = "idx_order_id", columnList = "order_id"),
        @Index(name = "idx_sku", columnList = "sku"),
        @Index(name = "idx_status", columnList = "status")
})
public class StockReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "order_id", nullable = false, length = 50)
    private String orderId; // Reference to Order Management

    @Column(name = "sku", nullable = false, length = 50)
    private String sku;

    @Column(name = "quantity_reserved")
    private Integer quantityReserved;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ReservationStatus status;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt; // Auto-release if order not paid

    @Column(name = "released_at")
    private LocalDateTime releasedAt;

    @Column(name = "fulfilled_at")
    private LocalDateTime fulfilledAt;

    // Getters and Setters

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantityReserved() {
        return quantityReserved;
    }

    public void setQuantityReserved(Integer quantityReserved) {
        this.quantityReserved = quantityReserved;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(LocalDateTime reservedAt) {
        this.reservedAt = reservedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getReleasedAt() {
        return releasedAt;
    }

    public void setReleasedAt(LocalDateTime releasedAt) {
        this.releasedAt = releasedAt;
    }

    public LocalDateTime getFulfilledAt() {
        return fulfilledAt;
    }

    public void setFulfilledAt(LocalDateTime fulfilledAt) {
        this.fulfilledAt = fulfilledAt;
    }

    @PrePersist
    protected void onCreate() {
        reservedAt = LocalDateTime.now();
        status = ReservationStatus.RESERVED;
        // Default expiration: 30 minutes
        expiresAt = reservedAt.plusMinutes(30);
    }

    /**
     * Check if reservation has expired
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt) && status == ReservationStatus.RESERVED;
    }

    /**
     * Release the reservation
     */
    public void release() {
        this.status = ReservationStatus.RELEASED;
        this.releasedAt = LocalDateTime.now();
    }

    /**
     * Mark as fulfilled (order shipped)
     */
    public void fulfill() {
        this.status = ReservationStatus.FULFILLED;
        this.fulfilledAt = LocalDateTime.now();
    }
}
