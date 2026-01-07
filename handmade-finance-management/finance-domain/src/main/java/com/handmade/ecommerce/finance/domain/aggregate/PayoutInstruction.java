package com.handmade.ecommerce.finance.domain.aggregate;

import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PayoutInstruction represents a specific intent to pay a seller.
 * Managed by STM (REQUESTED -> PROCESSING -> COMPLETED/FAILED)
 */
@Entity
@Table(name = "hm_finance_payout_instructions")
public class PayoutInstruction extends AbstractJpaStateEntity {

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Column(name = "amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", length = 3)
    private String currency = "USD";

    @Column(name = "bank_account_info")
    private String bankAccountInfo;

    @Column(name = "payout_method")
    private String payoutMethod; // STRIPE, WIRE, etc.

    @Column(name = "external_payout_id")
    private String externalPayoutId;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    // Getters and Setters
    public String getSellerId() { return sellerId; }
    public void setSellerId(String id) { this.sellerId = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getExternalPayoutId() { return externalPayoutId; }
    public void setExternalPayoutId(String id) { this.externalPayoutId = id; }

    public void setCompletedAt(LocalDateTime time) { this.completedAt = time; }

    @Override
    protected String getPrefix() {
        return "PAYOUT";
    }
}
