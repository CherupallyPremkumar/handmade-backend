package com.handmade.ecommerce.pricing.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Price History Entity
 * Tracks price changes for audit purposes
 */
@Entity
@Table(name = "hm_price_history")
@Getter
@Setter
public class PriceHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_id", nullable = false)
    @JsonBackReference
    private Price price;
    
    @Column(name = "old_price", precision = 10, scale = 2)
    private BigDecimal oldPrice;
    
    @Column(name = "new_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal newPrice;
    
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    
    @Column(name = "changed_by")
    private String changedBy;
    
    @Column(name = "change_reason", length = 500)
    private String changeReason;
    
    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;
    
    /**
     * Get price change amount
     */
    public BigDecimal getChangeAmount() {
        if (oldPrice == null) {
            return newPrice;
        }
        return newPrice.subtract(oldPrice);
    }
    
    /**
     * Get price change percentage
     */
    public BigDecimal getChangePercentage() {
        if (oldPrice == null || oldPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return getChangeAmount()
            .divide(oldPrice, 4, BigDecimal.ROUND_HALF_UP)
            .multiply(new BigDecimal("100"));
    }
}
