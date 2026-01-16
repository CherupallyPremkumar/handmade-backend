package com.handmade.ecommerce.policy.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_policy_evaluation_log")
public class PolicyEvaluationLog extends BaseJpaEntity {

    @Column(name = "decision_id", length = 36, nullable = false)
    private String decisionId;

    @Column(name = "rule_id", length = 36)
    private String ruleId;

    @Column(name = "evaluation_order")
    private Integer evaluationOrder;

    @Column(name = "matched_boolean")
    private Boolean matchedBoolean;

    @Column(name = "contribution", length = 50)
    private String contribution; // DECISIVE, OVERRIDDEN, IRRELEVANT

    @Lob
    @Column(name = "rule_output_json", columnDefinition = "TEXT")
    private String ruleOutputJson;
}
