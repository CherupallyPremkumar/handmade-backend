package com.handmade.ecommerce.pricing.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_pricing_rule_condition")
public class PricingRuleCondition extends BaseJpaEntity {

    @Column(name = "rule_id", length = 36, nullable = false)
    private String ruleDefinitionId;

    @Lob
    @Column(name = "condition_json", nullable = false)
    private String conditionJson;
}
