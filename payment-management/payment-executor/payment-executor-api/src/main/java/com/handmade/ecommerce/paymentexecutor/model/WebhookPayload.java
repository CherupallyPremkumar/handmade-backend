package com.handmade.ecommerce.paymentexecutor.model;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Normalized webhook payload from payment service providers
 */
@Data
public class WebhookPayload {
    private String eventType;
    private String paymentId;
    private String orderId;
    private String status;
    private Double amount;
    private String currency;
    private LocalDateTime timestamp;
    private String rawPayload;
}
