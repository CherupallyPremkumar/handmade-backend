package com.handmade.ecommerce.domain.policy;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_policy_rule")
public class PolicyRule extends BaseJpaEntity {

    @Column(name = "policy_definition_id", length = 36, nullable = false)
    private String policyDefinitionId;

    @Column(name = "rule_name")
    private String ruleName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "condition_json", columnDefinition = "TEXT", nullable = false)
    private String conditionJson;

    @Column(name = "effect_json", columnDefinition = "TEXT", nullable = false)
    private String effectJson;

    @Column(name = "priority")
    private Integer priority = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;
}
