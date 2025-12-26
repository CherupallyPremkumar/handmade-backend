package com.handmade.ecommerce.inventory.model;

/**
 * ReservationStatus - Enum
 * Status of stock reservations
 */
public enum ReservationStatus {
    RESERVED, // Stock reserved for order
    RELEASED, // Reservation released (order cancelled/expired)
    FULFILLED // Reservation fulfilled (order shipped)
}
