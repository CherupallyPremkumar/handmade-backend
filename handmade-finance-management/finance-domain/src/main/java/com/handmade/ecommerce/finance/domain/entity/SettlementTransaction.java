package com.handmade.ecommerce.finance.domain.entity;

import com.handmade.ecommerce.finance.domain.aggregate.SettlementAccount;
import org.chenile.jpautils.entity.BaseJpaEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Immutable record of a financial transaction within a SettlementAccount
 */
@Entity
@Table(name = "hm_finance_settlement_transactions")
public class SettlementTransaction extends BaseJpaEntity {

    public enum Type {
        ORDER_SETTLEMENT, PAYOUT, REFUND, CHARGEBACK, ADJUSTMENT
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private SettlementAccount account;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private Type type;

    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "reference_id")
    private String referenceId; // External ID (Order ID, Payout ID, etc.)

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime = LocalDateTime.now();

    // Getters and Setters
    public SettlementAccount getAccount() {
        return account;
    }

    public void setAccount(SettlementAccount account) {
        this.account = account;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String id) {
        this.referenceId = id;
    }

    @Override
    protected String getPrefix() {
        return "FIN_TX";
    }
}
