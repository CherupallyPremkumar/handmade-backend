package com.handmade.ecommerce.rules.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Database entity for tax rules by region
 * Tax rules are loaded from database - no code changes for new regions!
 */
@Entity
@Table(name = "tax_rules")
@Getter
@Setter
public class TaxRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Region code (ISO 3166-1 alpha-2)
     * Examples: IN, US, GB, CA, DE
     */
    @Column(nullable = false, length = 10)
    private String regionCode;

    /**
     * State/Province code (optional for state-level taxes)
     * Examples: CA, NY, TX for USA
     */
    @Column(length = 10)
    private String stateCode;

    /**
     * Tax name for display
     * Examples: "GST", "VAT", "Sales Tax"
     */
    @Column(nullable = false, length = 50)
    private String taxName;

    /**
     * Tax rate as percentage
     * Examples: 18.00 for 18%, 7.25 for 7.25%
     */
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal taxRate;

    /**
     * Tax type: INCLUSIVE (tax included in price) or EXCLUSIVE (tax added)
     */
    @Column(nullable = false, length = 20)
    private String taxType = "EXCLUSIVE";

    /**
     * Product category for category-specific tax rates (optional)
     */
    @Column(length = 50)
    private String productCategory;

    /**
     * Priority for rule matching (lower = higher priority)
     */
    @Column(nullable = false)
    private Integer priority = 100;

    /**
     * Is this rule currently active?
     */
    @Column(nullable = false)
    private Boolean isActive = true;

    /**
     * Valid from date
     */
    @Column
    private LocalDateTime validFrom;

    /**
     * Valid until date
     */
    @Column
    private LocalDateTime validUntil;

    /**
     * Description/notes
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
