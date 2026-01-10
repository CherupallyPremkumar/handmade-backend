package com.handmade.ecommerce.platform.policy.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_policy_scope
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_policy_scope")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PolicyScope extends BaseJpaEntity {
    
    @Column(name = "rule_id", nullable = false, length = 36)
    private String ruleId;
    @Column(name = "scope_dimension", nullable = false, length = 50)
    private String scopeDimension;
    @Column(name = "scope_value", nullable = false, length = 255)
    private String scopeValue;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
