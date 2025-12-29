package com.handmade.ecommerce.ledger.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.math.BigDecimal;

/**
 * LedgerLine represents a single line in double-entry bookkeeping.
 * Each transaction has at least 2 lines: one debit and one credit.
 */
@Getter
@Setter
@Entity
@Table(name = "hm_ledger_line")
public class LedgerLine extends BaseJpaEntity {

    /**
     * Reference to parent ledger
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_id", nullable = false)
    private Ledger ledger;

    /**
     * Account name (PLATFORM_RECEIVABLE, SELLER_A_PAYABLE, etc.)
     */
    @Column(name = "account", nullable = false)
    private String account;

    /**
     * Entry type (DEBIT or CREDIT)
     */
    @Column(name = "entry_type", nullable = false)
    private String entryType;

    /**
     * Amount
     */
    @Column(name = "amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;

    /**
     * Currency code (INR, USD, etc.)
     */
    @Column(name = "currency", length = 3)
    private String currency;

    /**
     * Description
     */
    @Column(name = "description")
    private String description;
}
