package com.handmade.ecommerce.shipping.model;

/**
 * ShipmentStatus - Enum
 * Status of a shipment
 */
public enum ShipmentStatus {
    PENDING,           // Shipment created, awaiting label
    LABEL_CREATED,     // Shipping label generated
    PICKED_UP,         // Picked up by carrier
    IN_TRANSIT,        // In transit to customer
    OUT_FOR_DELIVERY,  // Out for delivery
    DELIVERED,         // Successfully delivered
    FAILED_DELIVERY,   // Delivery attempt failed
    RETURNED           // Returned to sender
}
