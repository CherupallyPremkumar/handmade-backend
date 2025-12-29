package com.handmade.ecommerce.stripe.configuration;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Global configuration for Stripe integration.
 * Centralizes SDK initialization and common properties.
 */
@Configuration
public class StripeIntegrationConfig {

    @Value("${stripe.api.key:}")
    private String stripeApiKey;

    /**
     * Initialize Stripe global API key.
     * Required for all Stripe SDK operations.
     */
    @PostConstruct
    public void init() {
        if (stripeApiKey != null && !stripeApiKey.isEmpty()) {
            Stripe.apiKey = stripeApiKey;
        }
    }
}
