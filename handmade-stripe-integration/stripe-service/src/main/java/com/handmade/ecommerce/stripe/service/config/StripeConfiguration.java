package com.handmade.ecommerce.stripe.service.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Stripe Configuration
 * Centralized Stripe SDK configuration
 */
@Configuration
public class StripeConfiguration {
    
    @Value("${stripe.api.key}")
    private String stripeApiKey;
    
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;
    
    @PostConstruct
    public void init() {
        // Set global Stripe API key
        Stripe.apiKey = stripeApiKey;
    }
}
