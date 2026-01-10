package com.handmade.ecommerce.promotion.experiment.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_experiment_assignment
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_experiment_assignment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ExperimentAssignment extends BaseJpaEntity {
    
    @Column(name = "experiment_key", nullable = false, length = 100)
    private String experimentKey;
    @Column(name = "variant_key", nullable = false, length = 50)
    private String variantKey;
    @Column(name = "entity_id", nullable = false, length = 100)
    private String entityId;
    @Column(name = "context_json")
    private String contextJson;
    @Column(name = "assigned_at", nullable = false)
    private Date assignedAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
