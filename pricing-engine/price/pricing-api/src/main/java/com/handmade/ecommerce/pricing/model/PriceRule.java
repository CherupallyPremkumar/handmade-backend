package com.handmade.ecommerce.pricing.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Price Rule Entity
 * Defines pricing rules (discounts, markups, etc.)
 */
@Entity
@Table(name = "hm_price_rule")
@Getter
@Setter
public class PriceRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_id", nullable = false)
    @JsonBackReference
    private Price price;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", nullable = false, length = 50)
    private PriceRuleType ruleType;
    
    /**
     * JSON field storing rule conditions
     * Example: {"minQuantity": 10, "customerSegment": "VIP"}
     */
    @Column(name = "rule_condition", columnDefinition = "JSON")
    private String ruleCondition;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "adjustment_type", nullable = false, length = 50)
    private AdjustmentType adjustmentType;
    
    @Column(name = "adjustment_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal adjustmentValue;
    
    @Column(name = "priority", nullable = false)
    private Integer priority = 0;
    
    @Column(name = "valid_from")
    private LocalDateTime validFrom;
    
    @Column(name = "valid_until")
    private LocalDateTime validUntil;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    /**
     * Apply rule to base price
     */
    public BigDecimal apply(BigDecimal basePrice) {
        if (!isValid()) {
            return basePrice;
        }
        
        return switch (adjustmentType) {
            case PERCENTAGE_OFF -> {
                BigDecimal discount = basePrice.multiply(adjustmentValue).divide(new BigDecimal("100"));
                yield basePrice.subtract(discount);
            }
            case FIXED_AMOUNT_OFF -> basePrice.subtract(adjustmentValue);
            case FIXED_PRICE -> adjustmentValue;
            case PERCENTAGE_MARKUP -> {
                BigDecimal markup = basePrice.multiply(adjustmentValue).divide(new BigDecimal("100"));
                yield basePrice.add(markup);
            }
            case FIXED_AMOUNT_MARKUP -> basePrice.add(adjustmentValue);
        };
    }
    
    /**
     * Check if rule is currently valid
     */
    public boolean isValid() {
        if (!isActive) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        boolean afterStart = validFrom == null || now.isAfter(validFrom);
        boolean beforeEnd = validUntil == null || now.isBefore(validUntil);
        return afterStart && beforeEnd;
    }
}
