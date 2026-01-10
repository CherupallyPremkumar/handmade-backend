package com.handmade.ecommerce.platform.policy.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_policy_rule
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_policy_rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PolicyRule extends BaseJpaEntity {
    
    @Column(name = "policy_definition_id", nullable = false, length = 36)
    private String policyDefinitionId;
    @Column(name = "rule_name", length = 255)
    private String ruleName;
    @Column(name = "description")
    private String description;
    @Column(name = "condition_json", nullable = false)
    private String conditionJson;
    @Column(name = "effect_json", nullable = false)
    private String effectJson;
    @Column(name = "priority")
    private String priority;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
