package com.handmade.ecommerce.stripe.service.webhook;

import com.handmade.ecommerce.stripe.api.StripeWebhookEventType;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Centralized Stripe Webhook Controller
 * Handles ALL Stripe webhooks and routes to appropriate handlers
 * 
 * Security: Verifies webhook signatures to prevent spoofing
 */
@RestController
@RequestMapping("/webhooks/stripe")
public class StripeWebhookController {
    
    private static final Logger logger = LoggerFactory.getLogger(StripeWebhookController.class);
    
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;
    
    private final StripeWebhookRouter webhookRouter;
    
    public StripeWebhookController(StripeWebhookRouter webhookRouter) {
        this.webhookRouter = webhookRouter;
    }
    
    /**
     * Single webhook endpoint for ALL Stripe events
     * Routes to appropriate handler based on event type
     */
    @PostMapping
    public ResponseEntity<String> handleWebhook(
        @RequestBody String payload,
        @RequestHeader("Stripe-Signature") String signature) {
        
        Event event;
        try {
            // CRITICAL: Verify webhook signature to prevent spoofing
            event = Webhook.constructEvent(payload, signature, webhookSecret);
            
        } catch (SignatureVerificationException e) {
            logger.error("Invalid Stripe webhook signature");
            return ResponseEntity.status(400).body("Invalid signature");
        }
        
        logger.info("Received Stripe webhook: type={}, id={}", 
                   event.getType(), event.getId());
        
        // Route to appropriate handler
        try {
            webhookRouter.route(event);
            return ResponseEntity.ok("Webhook processed");
            
        } catch (Exception e) {
            logger.error("Failed to process Stripe webhook: type={}, id={}", 
                        event.getType(), event.getId(), e);
            // Return 200 to prevent Stripe from retrying
            // Log error for manual investigation
            return ResponseEntity.ok("Webhook received but processing failed");
        }
    }
}
