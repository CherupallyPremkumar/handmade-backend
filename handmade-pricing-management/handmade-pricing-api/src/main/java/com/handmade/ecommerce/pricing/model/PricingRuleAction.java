package com.handmade.ecommerce.pricing.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_pricing_rule_action")
public class PricingRuleAction extends BaseJpaEntity {

    @Column(name = "rule_id", length = 36, nullable = false)
    private String ruleDefinitionId;

    @Column(name = "action_type", length = 50, nullable = false)
    private String actionType; // PERCENTAGE_OFF, FIXED_AMOUNT, OVERRIDE

    @Column(name = "value", precision = 19, scale = 4, nullable = false)
    private BigDecimal value;

    @Column(name = "currency", length = 3)
    private String currency;
}
