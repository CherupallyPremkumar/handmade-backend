package com.handmade.ecommerce.payment.dto.webhook;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Razorpay Webhook DTO
 * Represents the payload received from Razorpay webhooks
 */
@Data
@NoArgsConstructor
public class RazorpayWebhook {

    private String event; // e.g., "payment.captured", "payment.failed"
    private String accountId;
    private RazorpayPayload payload;
    private Long created_at;

    @Data
    @NoArgsConstructor
    public static class RazorpayPayload {
        private RazorpayPaymentEntity payment;
        private RazorpayOrderEntity order;
    }

    @Data
    @NoArgsConstructor
    public static class RazorpayPaymentEntity {
        private RazorpayPaymentEntityDetails entity;
    }

    @Data
    @NoArgsConstructor
    public static class RazorpayPaymentEntityDetails {
        private String id;
        private String order_id;
        private String status; // "captured", "failed"
        private String method;
        private Long amount;
        private String currency;
        private String email;
        private String contact;
    }

    @Data
    @NoArgsConstructor
    public static class RazorpayOrderEntity {
        private RazorpayOrderEntityDetails entity;
    }

    @Data
    @NoArgsConstructor
    public static class RazorpayOrderEntityDetails {
        private String id;
        private String receipt; // This is our PaymentOrder ID!
    }
}
