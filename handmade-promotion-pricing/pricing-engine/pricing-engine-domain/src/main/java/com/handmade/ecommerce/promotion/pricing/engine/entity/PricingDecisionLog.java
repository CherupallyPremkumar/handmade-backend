package com.handmade.ecommerce.promotion.pricing.engine.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_pricing_decision_log
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_pricing_decision_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PricingDecisionLog extends BaseJpaEntity {
    
    @Column(name = "transaction_id", nullable = false, length = 36)
    private String transactionId;
    @Column(name = "original_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal originalPrice;
    @Column(name = "final_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal finalPrice;
    @Column(name = "applied_rules_json", nullable = false)
    private String appliedRulesJson;
    @Column(name = "decision_time")
    private Date decisionTime;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
