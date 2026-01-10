package com.handmade.ecommerce.promotion.experiment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_experiment_bucket
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_experiment_bucket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ExperimentBucket extends BaseJpaEntity {
    
    @Column(name = "experiment_id", nullable = false, length = 36)
    private String experimentId;
    @Column(name = "variant_key", nullable = false, length = 50)
    private String variantKey;
    @Column(name = "payload_json")
    private String payloadJson;
    @Column(name = "allocation_weight")
    private String allocationWeight;
    @Column(name = "is_control")
    private Boolean isControl;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
