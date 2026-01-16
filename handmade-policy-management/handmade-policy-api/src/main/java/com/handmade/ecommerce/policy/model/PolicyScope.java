package com.handmade.ecommerce.policy.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_policy_scope")
public class PolicyScope extends BaseJpaEntity {

    @Column(name = "rule_id", length = 36, nullable = false)
    private String ruleId;

    @Column(name = "scope_dimension", length = 50, nullable = false)
    private String scopeDimension; // REGION, TENANT, ENVIRONMENT

    @Column(name = "scope_value", nullable = false)
    private String scopeValue; // US, production
}
