package com.handmade.ecommerce.stripe.api;

/**
 * Stripe Webhook Event Types
 * Centralized enum for all Stripe webhook events we handle
 */
public enum StripeWebhookEventType {
    
    // Tax events
    TAX_REGISTRATION_COMPLETED("tax.registration.completed"),
    TAX_REGISTRATION_FAILED("tax.registration.failed"),
    
    // Payment events (future)
    PAYMENT_INTENT_SUCCEEDED("payment_intent.succeeded"),
    PAYMENT_INTENT_FAILED("payment_intent.failed"),
    
    // Payout events (future)
    PAYOUT_CREATED("payout.created"),
    PAYOUT_PAID("payout.paid"),
    PAYOUT_FAILED("payout.failed"),
    
    // Identity events (future - for KYC)
    IDENTITY_VERIFICATION_SESSION_VERIFIED("identity.verification_session.verified"),
    IDENTITY_VERIFICATION_SESSION_REQUIRES_INPUT("identity.verification_session.requires_input");
    
    private final String eventType;
    
    StripeWebhookEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public static StripeWebhookEventType fromString(String eventType) {
        for (StripeWebhookEventType type : values()) {
            if (type.eventType.equals(eventType)) {
                return type;
            }
        }
        return null;
    }
}
