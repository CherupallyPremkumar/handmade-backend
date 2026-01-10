package com.handmade.ecommerce.platform.risk.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_fraud_case
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_fraud_case")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FraudCase extends BaseJpaEntity {
    
    @Column(name = "reference_id", nullable = false, length = 36)
    private String referenceId;
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    @Column(name = "investigator_id", length = 36)
    private String investigatorId;
    @Column(name = "outcome", length = 50)
    private String outcome;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
