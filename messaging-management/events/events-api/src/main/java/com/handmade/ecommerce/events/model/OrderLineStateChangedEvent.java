package com.handmade.ecommerce.events.model;

import org.springframework.context.ApplicationEvent;

public class OrderLineStateChangedEvent extends ApplicationEvent {

    private final String orderId; // Only ID, not full Order object
    private final String lineId;
    private final String newState;

    public OrderLineStateChangedEvent(Object source, String orderId, String lineId, String newState) {
        super(source);
        this.orderId = orderId;
        this.lineId = lineId;
        this.newState = newState;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getLineId() {
        return lineId;
    }

    public String getNewState() {
        return newState;
    }
}
