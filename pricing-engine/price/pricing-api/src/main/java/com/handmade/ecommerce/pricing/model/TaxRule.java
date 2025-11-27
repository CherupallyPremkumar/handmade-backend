package com.handmade.ecommerce.pricing.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Tax Rule Entity
 * Defines tax rules for regions and product categories
 */
@Entity
@Table(name = "hm_tax_rule")
@Getter
@Setter
public class TaxRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    @JsonBackReference
    private Region region;
    
    @Column(name = "product_category_id")
    private Long productCategoryId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tax_type", nullable = false, length = 50)
    private TaxType taxType;
    
    @Column(name = "tax_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal taxRate;
    
    @Column(name = "is_inclusive", nullable = false)
    private Boolean isInclusive = false;
    
    @Column(name = "effective_from")
    private LocalDateTime effectiveFrom;
    
    @Column(name = "effective_to")
    private LocalDateTime effectiveTo;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    /**
     * Calculate tax amount
     */
    public BigDecimal calculateTax(BigDecimal price) {
        if (!isEffective()) {
            return BigDecimal.ZERO;
        }
        
        if (isInclusive) {
            // Tax is already included in price, extract it
            BigDecimal divisor = BigDecimal.ONE.add(taxRate.divide(new BigDecimal("100")));
            BigDecimal priceWithoutTax = price.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
            return price.subtract(priceWithoutTax);
        } else {
            // Tax needs to be added
            return price.multiply(taxRate).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
        }
    }
    
    /**
     * Check if tax rule is currently effective
     */
    public boolean isEffective() {
        if (!isActive) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        boolean afterStart = effectiveFrom == null || now.isAfter(effectiveFrom);
        boolean beforeEnd = effectiveTo == null || now.isBefore(effectiveTo);
        return afterStart && beforeEnd;
    }
}
