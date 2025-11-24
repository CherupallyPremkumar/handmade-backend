package com.handmade.ecommerce.events.model;

import org.springframework.context.ApplicationEvent;

public class OrderLineEvent extends ApplicationEvent {

    public enum ActionType {
        ADD,
        UPDATE,
        INCREMENT,
        DECREMENT,
        REMOVE,
        FULFILLED // optional for fulfilled lines
    }

    private String orderId;
    private ActionType actionType;

    /**
     * Constructor for Spring ApplicationEvent
     *
     * @param source The object on which the event initially occurred (usually 'this' or the publisher)
     * @param orderId Parent order ID
     * @param actionType Action type (ADD, UPDATE, FULFILLED, etc.)
     */
    public OrderLineEvent(Object source, String orderId, ActionType actionType) {
        super(source); // must pass source to ApplicationEvent constructor
        this.orderId = orderId;
        this.actionType = actionType;
    }

    // Getters
    public String getOrderId() { return orderId; }
    public ActionType getActionType() { return actionType; }


    // Setters
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setActionType(ActionType actionType) { this.actionType = actionType; }
}