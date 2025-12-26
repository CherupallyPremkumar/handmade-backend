package com.handmade.ecommerce.stripe.service.webhook;

import com.handmade.ecommerce.stripe.api.StripeWebhookEventType;
import com.stripe.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Stripe Webhook Router
 * Routes Stripe webhook events to appropriate domain event publishers
 * 
 * Pattern: Converts Stripe events → Spring ApplicationEvents
 * This decouples Stripe from domain logic
 */
@Component
public class StripeWebhookRouter {
    
    private static final Logger logger = LoggerFactory.getLogger(StripeWebhookRouter.class);
    
    private final ApplicationEventPublisher eventPublisher;
    
    public StripeWebhookRouter(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * Route Stripe webhook to appropriate handler
     */
    public void route(Event stripeEvent) {
        StripeWebhookEventType eventType = StripeWebhookEventType.fromString(stripeEvent.getType());
        
        if (eventType == null) {
            logger.warn("Unhandled Stripe webhook event type: {}", stripeEvent.getType());
            return;
        }
        
        switch (eventType) {
            case TAX_REGISTRATION_COMPLETED:
                handleTaxRegistrationCompleted(stripeEvent);
                break;
                
            case TAX_REGISTRATION_FAILED:
                handleTaxRegistrationFailed(stripeEvent);
                break;
                
            // Future: Add payment, payout, identity events
            
            default:
                logger.info("Stripe webhook event not yet implemented: {}", eventType);
        }
    }
    
    private void handleTaxRegistrationCompleted(Event stripeEvent) {
        logger.info("Tax registration completed: {}", stripeEvent.getId());
        
        // Extract registration ID from event
        String registrationId = extractRegistrationId(stripeEvent);
        String entityId = extractMetadata(stripeEvent, "entity_id");
        String entityType = extractMetadata(stripeEvent, "entity_type");
        
        // Publish Spring event for domain layer to handle
        // This decouples Stripe from seller-management
        eventPublisher.publishEvent(
            new TaxRegistrationCompletedEvent(registrationId, entityId, entityType)
        );
    }
    
    private void handleTaxRegistrationFailed(Event stripeEvent) {
        logger.warn("Tax registration failed: {}", stripeEvent.getId());
        
        String registrationId = extractRegistrationId(stripeEvent);
        String entityId = extractMetadata(stripeEvent, "entity_id");
        
        eventPublisher.publishEvent(
            new TaxRegistrationFailedEvent(registrationId, entityId)
        );
    }
    
    private String extractRegistrationId(Event event) {
        // Extract from event data
        // Implementation depends on Stripe API structure
        return event.getData().getObject().toString(); // Placeholder
    }
    
    private String extractMetadata(Event event, String key) {
        // Extract metadata from event
        // Implementation depends on Stripe API structure
        return ""; // Placeholder
    }
    
    /**
     * Spring event published when tax registration completes
     * Seller-management module listens to this event
     */
    public static class TaxRegistrationCompletedEvent {
        public final String registrationId;
        public final String entityId;
        public final String entityType;
        
        public TaxRegistrationCompletedEvent(String registrationId, String entityId, String entityType) {
            this.registrationId = registrationId;
            this.entityId = entityId;
            this.entityType = entityType;
        }
    }
    
    public static class TaxRegistrationFailedEvent {
        public final String registrationId;
        public final String entityId;
        
        public TaxRegistrationFailedEvent(String registrationId, String entityId) {
            this.registrationId = registrationId;
            this.entityId = entityId;
        }
    }
}
