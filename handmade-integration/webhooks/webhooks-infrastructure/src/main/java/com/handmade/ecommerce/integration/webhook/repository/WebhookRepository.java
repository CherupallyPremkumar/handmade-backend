package com.handmade.ecommerce.integration.webhook;

import com.handmade.ecommerce.integration.webhook.entity.Webhook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Webhook
 * Generated from entity file
 */
@Repository
public interface WebhookRepository extends JpaRepository<Webhook, String> {
    
    List<Webhook> findByEventType(String eventType);
    List<Webhook> findBySecretKey(String secretKey);
    List<Webhook> findByStatus(String status);
}
