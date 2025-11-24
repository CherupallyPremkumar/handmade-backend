package com.handmade.ecommerce.events.model;

public class OrderLineShippedEvent {
    private final String orderId;
    private final String orderLineId;

    public OrderLineShippedEvent(String orderId, String orderLineId) {
        this.orderId = orderId;
        this.orderLineId = orderLineId;
    }

    public String getOrderId() { return orderId; }
    public String getOrderLineId() { return orderLineId; }
}