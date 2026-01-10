package com.handmade.ecommerce.promotion.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_promotion_rule
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_promotion_rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PromotionRule extends BaseJpaEntity {
    
    @Column(name = "promotion_id", nullable = false, length = 36)
    private String promotionId;
    @Column(name = "rule_type", nullable = false, length = 100)
    private String ruleType;
    @Column(name = "rule_key", nullable = false, length = 255)
    private String ruleKey;
    @Column(name = "operator", nullable = false, length = 50)
    private String operator;
    @Column(name = "rule_value", nullable = false)
    private String ruleValue;
    @Column(name = "rule_priority")
    private String rulePriority;
    @Column(name = "is_required")
    private Boolean isRequired;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
