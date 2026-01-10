package com.handmade.ecommerce.promotion.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_promotion
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_promotion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Promotion extends BaseJpaEntity {
    
    @Column(name = "promotion_code", nullable = false, length = 100, unique = true)
    private String promotionCode;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "promotion_type", nullable = false, length = 50)
    private String promotionType;
    @Column(name = "discount_type", nullable = false, length = 50)
    private String discountType;
    @Column(name = "discount_value", precision = 19, scale = 4)
    private BigDecimal discountValue;
    @Column(name = "max_discount_amount", precision = 19, scale = 4)
    private BigDecimal maxDiscountAmount;
    @Column(name = "min_purchase_amount", precision = 19, scale = 4)
    private BigDecimal minPurchaseAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "target_customer_segment", length = 100)
    private String targetCustomerSegment;
    @Column(name = "target_product_category", length = 100)
    private String targetProductCategory;
    @Column(name = "priority")
    private String priority;
    @Column(name = "is_stackable")
    private Boolean isStackable;
    @Column(name = "max_uses_per_customer")
    private String maxUsesPerCustomer;
    @Column(name = "max_total_uses")
    private String maxTotalUses;
    @Column(name = "current_uses")
    private String currentUses;
    @Column(name = "budget_amount", precision = 19, scale = 4)
    private BigDecimal budgetAmount;
    @Column(name = "spent_amount", precision = 19, scale = 4)
    private BigDecimal spentAmount;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
