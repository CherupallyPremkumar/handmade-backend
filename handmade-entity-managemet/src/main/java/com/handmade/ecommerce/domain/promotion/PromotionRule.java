package com.handmade.ecommerce.domain.promotion;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * PromotionRule - Rules defining promotion eligibility
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_promotion_rule")
public class PromotionRule extends BaseJpaEntity {

    @Column(name = "promotion_id", length = 36, nullable = false)
    private String promotionId;

    @Column(name = "rule_type", length = 100, nullable = false)
    private String ruleType; // CART_TOTAL, PRODUCT_CATEGORY, CUSTOMER_SEGMENT

    @Column(name = "rule_key", length = 255)
    private String ruleKey;

    @Column(name = "operator", length = 50, nullable = false)
    private String operator; // GT, LT, EQ, IN, CONTAINS

    @Lob
    @Column(name = "rule_value", nullable = false, columnDefinition = "TEXT")
    private String value;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "rule_priority")
    private Integer rulePriority = 0;

    @Column(name = "is_required")
    private Boolean isRequired = true;
}
