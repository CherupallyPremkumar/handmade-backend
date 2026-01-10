package com.handmade.ecommerce.platform.policy.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_policy_evaluation_log
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_policy_evaluation_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PolicyEvaluationLog extends BaseJpaEntity {
    
    @Column(name = "decision_id", nullable = false, length = 36)
    private String decisionId;
    @Column(name = "rule_id", length = 36)
    private String ruleId;
    @Column(name = "evaluation_order")
    private String evaluationOrder;
    @Column(name = "matched_boolean")
    private Boolean matchedBoolean;
    @Column(name = "contribution", length = 50)
    private String contribution;
    @Column(name = "rule_output_json")
    private String ruleOutputJson;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
