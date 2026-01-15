package com.handmade.ecommerce.domain.promotion;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * PromotionRule - Rules defining promotion eligibility
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_promotion_rule")
public class PromotionRule extends BaseJpaEntity {

    @Column(name = "promotion_id", length = 36, nullable = false)
    private String promotionId;

    @Column(name = "rule_type", length = 50, nullable = false)
    private String ruleType; // CART_TOTAL, PRODUCT_CATEGORY, CUSTOMER_SEGMENT

    @Column(name = "operator", length = 50, nullable = false)
    private String operator; // GT, LT, EQ, IN, CONTAINS

    @Column(name = "value", columnDefinition = "TEXT")
    private String value;

    @Column(name = "description", length = 500)
    private String description;
}
