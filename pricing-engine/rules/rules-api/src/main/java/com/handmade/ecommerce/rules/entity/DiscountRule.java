package com.handmade.ecommerce.rules.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Database entity for discount rules
 * Rules are loaded from database and evaluated dynamically
 * 
 * No code changes needed to add new rules - just insert into database!
 */
@Entity
@Table(name = "discount_rules")
@Getter
@Setter
public class DiscountRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Rule name for display (e.g., "Bulk Discount", "VIP Discount")
     */
    @Column(nullable = false, length = 100)
    private String ruleName;

    /**
     * Rule type for categorization
     * Examples: BULK_DISCOUNT, CUSTOMER_SEGMENT, FIRST_ORDER, TIME_BASED, SEASONAL
     */
    @Column(nullable = false, length = 50)
    private String ruleType;

    /**
     * Condition field to check
     * Examples: quantity, customerSegment, isFirstOrder, regionCode
     */
    @Column(nullable = false, length = 100)
    private String conditionField;

    /**
     * Condition operator
     * Examples: >=, ==, IN, BETWEEN
     */
    @Column(nullable = false, length = 20)
    private String conditionOperator;

    /**
     * Condition value(s) - can be JSON for complex conditions
     * Examples: "10", "VIP", "[\"VIP\", \"PREMIUM\"]"
     */
    @Column(nullable = false, length = 500)
    private String conditionValue;

    /**
     * Discount type: PERCENTAGE or FIXED_AMOUNT
     */
    @Column(nullable = false, length = 20)
    private String discountType;

    /**
     * Discount value
     * For PERCENTAGE: 10 = 10%
     * For FIXED_AMOUNT: 100 = ₹100 off
     */
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal discountValue;

    /**
     * Priority for rule ordering (lower = higher priority)
     */
    @Column(nullable = false)
    private Integer priority = 100;

    /**
     * Scope: GLOBAL (all products), CATEGORY, SELLER, PRODUCT
     */
    @Column(nullable = false, length = 20)
    private String scope = "GLOBAL";

    /**
     * Scope value (category_id, seller_id, product_id, or null for GLOBAL)
     */
    @Column(length = 100)
    private String scopeValue;

    /**
     * Can this rule be combined with other rules?
     */
    @Column(nullable = false)
    private Boolean stackable = true;

    /**
     * Maximum discount amount (cap)
     */
    @Column(precision = 19, scale = 4)
    private BigDecimal maxDiscountAmount;

    /**
     * When rule becomes active
     */
    @Column
    private LocalDateTime validFrom;

    /**
     * When rule expires
     */
    @Column
    private LocalDateTime validUntil;

    /**
     * Is rule currently active?
     */
    @Column(nullable = false)
    private Boolean isActive = true;

    /**
     * Description for admin/display
     */
    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
