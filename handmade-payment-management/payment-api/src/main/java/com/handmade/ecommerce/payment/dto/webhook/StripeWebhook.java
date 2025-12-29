package com.handmade.ecommerce.payment.dto.webhook;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stripe Webhook DTO
 * Represents the payload received from Stripe webhooks
 */
@Data
@NoArgsConstructor
public class StripeWebhook {

    private String id;
    private String type; // e.g., "checkout.session.completed"
    private StripeData data;
    private Long created;

    @Data
    @NoArgsConstructor
    public static class StripeData {
        private StripeObject object;
    }

    @Data
    @NoArgsConstructor
    public static class StripeObject {
        private String id; // cs_test_...
        private String payment_intent;
        private String payment_status; // "paid"
        private Long amount_total;
        private String currency;
        private StripeMetadata metadata;
        private String client_reference_id; // Can be used for our Order ID
    }

    @Data
    @NoArgsConstructor
    public static class StripeMetadata {
        private String payment_order_id; // Our internal ID
    }
}
