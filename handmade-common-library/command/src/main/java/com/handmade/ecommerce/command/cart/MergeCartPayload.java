package com.handmade.ecommerce.command.cart;

/**
 * Payload for linking guest cart to user after login
 */
public class MergeCartPayload {

    /**
     * User ID to link the cart to
     */
    private String userId;

    /**
     * Session ID for validation
     */
    private String sessionId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
