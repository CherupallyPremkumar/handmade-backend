package com.handmade.ecommerce.seller.domain.entity;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "seller_payment_method")
public class PaymentMethodd extends BaseJpaEntity {

    // 🔹 Relationship to SellerPaymentInfo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_info_id", nullable = false)
    private SellerPaymentInfo paymentInfo;

    // ==============================================================
    // 🔹 External Payment Provider Integration
    // ==============================================================

    /**
     * Payment processor or provider handling this method.
     * Example: STRIPE, PAYPAL, RAZORPAY, ADYEN, etc.
     */
    @Column(name = "external_provider", length = 50)
    private String externalProvider;

    /**
     * Unique method ID assigned by the payment provider.
     * Example: Stripe’s `pm_1234abcd`
     */
    @Column(name = "external_method_id", length = 100)
    private String externalMethodId;

    /**
     * Masked last 4 digits (useful for display only).
     * Example: “4242” for Visa card or last 4 of bank account.
     */
    @Column(name = "last4", length = 4)
    private String last4;

    // ==============================================================
    // 🔹 Optional Local Banking / Account Details (Non-sensitive)
    // ==============================================================

    @Column(name = "account_holder_name", length = 100)
    private String accountHolderName;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    /**
     * Only store masked or partial account numbers.
     * Example: “XXXXXX1234”
     */
    @Column(name = "account_number", length = 50)
    private String accountNumber;

    @Column(name = "ifsc_code", length = 20)
    private String ifscCode;

    @Column(name = "swift_code", length = 50)
    private String swiftCode;

    @Column(name = "paypal_email", length = 100)
    private String paypalEmail;

    @Column(name = "stripe_account_id", length = 100)
    private String stripeAccountId;

    @Column(name = "upi_id", length = 50)
    private String upiId;

    // ==============================================================
    // 🔹 Metadata and Status
    // ==============================================================

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    /**
     * Current status of the payment method.
     * Allowed values: PENDING_VERIFICATION / ACTIVE / INACTIVE / FAILED
     */
    @Column(name = "status", length = 30)
    private String status = "PENDING_VERIFICATION";

    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDateTime addedAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // ==============================================================
    // 🔹 Utility Methods
    // ==============================================================

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ==============================================================
    // 🔹 Getters and Setters
    // ==============================================================

    public SellerPaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(SellerPaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getExternalProvider() {
        return externalProvider;
    }

    public void setExternalProvider(String externalProvider) {
        this.externalProvider = externalProvider;
    }

    public String getExternalMethodId() {
        return externalMethodId;
    }

    public void setExternalMethodId(String externalMethodId) {
        this.externalMethodId = externalMethodId;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public void setPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    public String getStripeAccountId() {
        return stripeAccountId;
    }

    public void setStripeAccountId(String stripeAccountId) {
        this.stripeAccountId = stripeAccountId;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
