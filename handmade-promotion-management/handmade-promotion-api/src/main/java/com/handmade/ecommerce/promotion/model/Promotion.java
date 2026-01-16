package com.handmade.ecommerce.promotion.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * Promotion - Core promotion entity managed by STM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_promotion")
public class Promotion extends AbstractJpaStateEntity {

    @Column(name = "promotion_code", length = 100, nullable = false, unique = true)
    private String promotionCode;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "promotion_type", length = 50, nullable = false)
    private String promotionType; // PERCENTAGE, FIXED_AMOUNT, BOGO

    @Column(name = "discount_type", length = 50)
    private String discountType;

    @Column(name = "discount_value")
    private java.math.BigDecimal discountValue;

    @Column(name = "max_discount_amount")
    private java.math.BigDecimal maxDiscountAmount;

    @Column(name = "min_purchase_amount")
    private java.math.BigDecimal minPurchaseAmount;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "target_customer_segment", length = 100)
    private String targetCustomerSegment;

    @Column(name = "target_product_category", length = 100)
    private String targetProductCategory;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "exclusive")
    private Boolean exclusive = false;

    @Column(name = "is_stackable")
    private Boolean isStackable = false;

    @Column(name = "max_uses_per_customer")
    private Integer maxUsesPerCustomer;

    @Column(name = "max_total_uses")
    private Integer maxTotalUses;

    @Column(name = "current_uses")
    private Integer currentUses = 0;

    @Column(name = "budget_amount")
    private java.math.BigDecimal budgetAmount;

    @Column(name = "spent_amount")
    private java.math.BigDecimal spentAmount = java.math.BigDecimal.ZERO;
}
