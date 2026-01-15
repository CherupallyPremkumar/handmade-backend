package com.handmade.ecommerce.domain.pricing;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_pricing_rule_condition")
public class PricingRuleCondition extends BaseJpaEntity {

    @Column(name = "rule_id", length = 36, nullable = false)
    private String ruleDefinitionId;

    @Column(name = "condition_json", columnDefinition = "TEXT", nullable = false)
    private String conditionJson;
}
