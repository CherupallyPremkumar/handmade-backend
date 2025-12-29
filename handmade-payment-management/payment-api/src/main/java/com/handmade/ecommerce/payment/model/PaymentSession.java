package com.handmade.ecommerce.payment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * PaymentSession Entity
 * 
 * Stores PSP (Payment Service Provider) session details for tracking
 * checkout sessions and payment tokens throughout the payment lifecycle.
 * 
 * Lifecycle:
 * 1. Created when PSP checkout session is created
 * 2. Updated when payment is completed (webhook)
 * 3. Used for idempotency, reconciliation, and refunds
 */
@Entity
@Table(name = "payment_session", indexes = {
        @Index(name = "idx_payment_id", columnList = "payment_id"),
        @Index(name = "idx_psp_session_id", columnList = "psp_session_id"),
        @Index(name = "idx_psp_payment_id", columnList = "psp_payment_id")
})
@Getter
@Setter
public class PaymentSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Reference to parent Payment entity
     */
    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    /**
     * PSP type used for this payment
     * Examples: RAZORPAY, STRIPE, PAYPAL
     */
    @Column(name = "psp_type", nullable = false, length = 50)
    private String pspType;

    /**
     * PSP's session identifier
     * Created when checkout session is initiated
     * Used to track the session before payment is completed
     */
    @Column(name = "psp_session_id", length = 255)
    private String pspSessionId;

    /**
     * Checkout URL where user completes payment
     * User is redirected to this URL to enter payment details
     */
    @Column(name = "checkout_url", length = 500)
    private String checkoutUrl;

    /**
     * PSP's payment identifier/token
     * Received from webhook after payment is completed
     * Used for refunds, captures, and reconciliation
     */
    @Column(name = "psp_payment_id", length = 255)
    private String pspPaymentId;

    /**
     * Session status
     * CREATED, ACTIVE, COMPLETED, EXPIRED, CANCELLED
     */
    @Column(name = "status", length = 50)
    private String status;

    /**
     * Session expiry time (from PSP)
     */
    @Column(name = "expires_at")
    private Instant expiresAt;

    /**
     * Additional metadata from PSP (JSON format)
     * Can store PSP-specific fields
     */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * Checks if this payment session is still active (not expired).
     * 
     * A session is considered active if:
     * - No expiry time is set (expiresAt is null), OR
     * - Current time is before the expiry time
     * 
     * @return true if session is active, false if expired
     */
    public boolean isActive() {
        if (expiresAt == null) {
            // No expiry set - consider active
            return true;
        }

        return Instant.now().isBefore(expiresAt);
    }

    /**
     * Checks if this payment session has expired.
     * 
     * @return true if session is expired, false if still active
     */
    public boolean isExpired() {
        return !isActive();
    }
}
