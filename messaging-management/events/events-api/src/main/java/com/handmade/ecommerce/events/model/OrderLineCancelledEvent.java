package com.handmade.ecommerce.events.model;

public class OrderLineCancelledEvent {
    private String orderId;
    private String orderLineId;

    public OrderLineCancelledEvent(String orderId, String orderLineId) {
        this.orderId = orderId;
        this.orderLineId = orderLineId;
    }

    public String getOrderId() { return orderId; }
    public String getOrderLineId() { return orderLineId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setOrderLineId(String orderLineId) { this.orderLineId = orderLineId; }
}
