package com.handmade.ecommerce.ledger.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Ledger represents a financial transaction header in double-entry bookkeeping.
 * Each Ledger has multiple LedgerLines (debit and credit entries).
 */
@Getter
@Setter
@Entity
@Table(name = "hm_ledger")
public class Ledger extends BaseJpaEntity {

    /**
     * Transaction date
     */
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    /**
     * Reference to external entity (PaymentOrder ID, Payout ID, etc.)
     */
    @Column(name = "reference_id")
    private String referenceId;

    /**
     * Reference type (PAYMENT_ORDER, PAYOUT, REFUND, etc.)
     */
    @Column(name = "reference_type")
    private String referenceType;

    /**
     * Transaction description
     */
    @Column(name = "description")
    private String description;

    /**
     * Total amount (for verification)
     */
    @Column(name = "total_amount", precision = 19, scale = 4)
    private BigDecimal totalAmount;

    /**
     * Currency code (INR, USD, etc.)
     */
    @Column(name = "currency", length = 3)
    private String currency;

    /**
     * Status (PENDING, SETTLED, REVERSED)
     */
    @Column(name = "status")
    private String status;

    /**
     * Ledger lines (debit and credit entries)
     */
    @OneToMany(mappedBy = "ledger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LedgerLine> lines = new ArrayList<>();

    /**
     * Helper method to add a ledger line
     */
    public void addLine(LedgerLine line) {
        lines.add(line);
        line.setLedger(this);
    }
}
