package com.handmade.ecommerce.pricing.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_pricing_priority")
public class PricingPriority extends BaseJpaEntity {

    @Column(name = "rule_id", length = 36, nullable = false)
    private String ruleDefinitionId;

    @Column(name = "priority_score", nullable = false)
    private Integer priorityScore; // Higher runs first

    @Column(name = "stacking_group", length = 50)
    private String stackingGroup; // e.g. "DEFAULT", "EXCLUSIVE"
}
