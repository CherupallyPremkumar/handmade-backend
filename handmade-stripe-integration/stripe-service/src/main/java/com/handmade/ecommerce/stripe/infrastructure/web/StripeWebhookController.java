package com.handmade.ecommerce.stripe.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Stripe webhooks.
 * Receives events from Stripe and processes them via the integration service.
 */
@RestController
@ChenileController(value = "stripeWebhookController", serviceName = "_identityWebhookService_", healthCheckerName = "stripeWebhookHealthChecker")
public class StripeWebhookController extends ControllerSupport {

    @PostMapping("/webhooks/stripe/identity")
    public ResponseEntity<Void> handleIdentityWebhook(
            HttpServletRequest request,
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sig) {

        process("handleIdentityWebhook", request, payload, sig);
        return ResponseEntity.ok().build();
    }
}
