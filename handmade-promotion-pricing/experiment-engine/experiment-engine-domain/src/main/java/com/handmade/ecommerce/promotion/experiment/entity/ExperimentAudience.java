package com.handmade.ecommerce.promotion.experiment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_experiment_audience
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_experiment_audience")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ExperimentAudience extends BaseJpaEntity {
    
    @Column(name = "experiment_id", nullable = false, length = 36)
    private String experimentId;
    @Column(name = "rule_name", length = 100)
    private String ruleName;
    @Column(name = "rule_json", nullable = false)
    private String ruleJson;
    @Column(name = "priority")
    private String priority;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
