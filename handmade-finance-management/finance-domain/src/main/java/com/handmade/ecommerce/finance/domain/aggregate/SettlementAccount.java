package com.handmade.ecommerce.finance.domain.aggregate;

import com.handmade.ecommerce.finance.domain.entity.SettlementTransaction;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import org.chenile.jpautils.entity.BaseJpaEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * SettlementAccount represents the financial state of a Seller.
 * Tracks balances in different lifecycle stages.
 */
@Entity
@Table(name = "hm_finance_settlement_accounts",
       indexes = {@Index(name = "idx_seller_settlement", columnList = "seller_id")})
public class SettlementAccount extends AbstractJpaStateEntity {

    @Column(name = "seller_id", unique = true, nullable = false)
    private String sellerId;

    /**
     * Total funds currently tracked in this account
     */
    @Column(name = "total_balance", precision = 19, scale = 4)
    private BigDecimal totalBalance = BigDecimal.ZERO;

    /**
     * Funds available for immediate withdrawal/payout
     */
    @Column(name = "available_balance", precision = 19, scale = 4)
    private BigDecimal availableBalance = BigDecimal.ZERO;

    /**
     * Funds held for the return window/settlement period
     */
    @Column(name = "pending_balance", precision = 19, scale = 4)
    private BigDecimal pendingBalance = BigDecimal.ZERO;

    /**
     * Funds withheld due to disputes or fraud investigation
     */
    @Column(name = "reserved_balance", precision = 19, scale = 4)
    private BigDecimal reservedBalance = BigDecimal.ZERO;

    @Column(name = "currency", length = 3)
    private String currency = "USD";

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SettlementTransaction> transactions = new ArrayList<>();

    // ========== BUSINESS LOGIC ==========

    /**
     * Process order settlement. Moves funds to pending.
     */
    public void creditOrder(BigDecimal orderAmount, BigDecimal platformFees) {
        BigDecimal netAmount = orderAmount.subtract(platformFees);
        this.pendingBalance = this.pendingBalance.add(netAmount);
        this.totalBalance = this.totalBalance.add(netAmount);
    }

    /**
     * Move funds from pending to available (after return window)
     */
    public void releaseFunds(BigDecimal amount) {
        if (this.pendingBalance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient pending funds to release");
        }
        this.pendingBalance = this.pendingBalance.subtract(amount);
        this.availableBalance = this.availableBalance.add(amount);
    }

    /**
     * Deduct funds for payout
     */
    public void deductPayout(BigDecimal amount) {
        if (this.availableBalance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient available funds for payout");
        }
        this.availableBalance = this.availableBalance.subtract(amount);
        this.totalBalance = this.totalBalance.subtract(amount);
    }

    // ========== GETTERS AND SETTERS ==========

    public String getSellerId() { return sellerId; }
    public void setSellerId(String id) { this.sellerId = id; }

    public BigDecimal getTotalBalance() { return totalBalance; }
    public BigDecimal getAvailableBalance() { return availableBalance; }
    public BigDecimal getPendingBalance() { return pendingBalance; }
    public BigDecimal getReservedBalance() { return reservedBalance; }

    @Override
    protected String getPrefix() {
        return "SETTLEMENT";
    }
}
