package com.handmade.ecommerce.pricing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Regional Price Entity
 * Stores region-specific pricing in local currency
 */
@Entity
@Table(name = "hm_regional_price")
@Getter
@Setter
public class RegionalPrice extends BaseJpaEntity {

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_id", nullable = false)
    @JsonBackReference
    private Price price;
    
    @Column(name = "region_id", nullable = false)
    private Long regionId;
    
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    
    @Column(name = "price_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAmount;
    
    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;
    
    @Column(name = "includes_tax", nullable = false)
    private Boolean includesTax = false;
    
    @Column(name = "effective_from")
    private LocalDateTime effectiveFrom;
    
    @Column(name = "effective_to")
    private LocalDateTime effectiveTo;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    /**
     * Get price with tax included
     */
    public BigDecimal getPriceWithTax() {
        if (includesTax) {
            return priceAmount;
        }
        if (taxRate == null) {
            return priceAmount;
        }
        BigDecimal taxAmount = priceAmount.multiply(taxRate).divide(new BigDecimal("100"));
        return priceAmount.add(taxAmount);
    }
    
    /**
     * Get price without tax
     */
    public BigDecimal getPriceWithoutTax() {
        if (!includesTax) {
            return priceAmount;
        }
        if (taxRate == null) {
            return priceAmount;
        }
        BigDecimal divisor = BigDecimal.ONE.add(taxRate.divide(new BigDecimal("100")));
        return priceAmount.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
    }
}
