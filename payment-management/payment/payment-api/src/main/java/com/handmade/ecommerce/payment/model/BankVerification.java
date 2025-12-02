package com.handmade.ecommerce.payment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "hm_bank_verification", indexes = {
        @Index(name = "idx_seller_id", columnList = "seller_id"),
        @Index(name = "idx_account_number", columnList = "account_number"),
        @Index(name = "idx_reference_id", columnList = "reference_id"),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_provider_name", columnList = "provider_name")
})
public class BankVerification extends BaseJpaEntity {

    @Column(name = "seller_id", nullable = false)
    private String sellerId;
    @Column(name = "account_number", nullable = false, length = 50)
    private String accountNumber;
    @Column(name = "ifsc_code", length = 20)
    private String ifscCode;
    @Column(name = "routing_number", length = 20)
    private String routingNumber;
    @Column(name = "swift_code", length = 20)
    private String swiftCode;
    @Column(name = "account_holder_name", length = 255)
    private String accountHolderName;
    @Column(name = "bank_name", length = 255)
    private String bankName;
    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;
    @Column(name = "account_type", length = 20)
    private String accountType;
    @Column(name = "provider_name", nullable = false, length = 50)
    private String providerName;
    @Column(name = "reference_id", unique = true, length = 255)
    private String referenceId;
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    /**
     * Verification method used
     * Values: PENNY_DROP, MICRO_DEPOSIT, INSTANT
     */
    @Column(name = "verification_method", length = 50)
    private String verificationMethod;
    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
    @Column(name = "verification_response", columnDefinition = "TEXT")
    private String verificationResponse;
    @Column(name = "amount_deposited")
    private Double amountDeposited;
    @Column(name = "error_code", length = 50)
    private String errorCode;
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
    @Column(name = "retry_count")
    private Integer retryCount = 0;
    @Column(name = "last_retry_at")
    private LocalDateTime lastRetryAt;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    private String maskAccountNumber() {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
}
