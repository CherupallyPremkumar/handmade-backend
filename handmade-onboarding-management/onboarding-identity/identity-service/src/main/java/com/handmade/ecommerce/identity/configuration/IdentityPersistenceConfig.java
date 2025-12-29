package com.handmade.ecommerce.identity.configuration;

import com.handmade.ecommerce.identity.infrastructure.webhook.WebhookIdempotencyRepository;
import com.handmade.ecommerce.identity.infrastructure.webhook.WebhookIdempotencyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Identity verification persistence components.
 * Independent of specific external providers.
 */
@Configuration
public class IdentityPersistenceConfig {

    /**
     * Idempotency service bean
     * Uses repository for DB-backed idempotency of external updates.
     */
    @Bean
    public WebhookIdempotencyService webhookIdempotencyService(WebhookIdempotencyRepository repository) {
        return new WebhookIdempotencyService(repository);
    }
}
