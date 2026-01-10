package com.handmade.ecommerce.platform.policy.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_policy_definition
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_policy_definition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PolicyDefinition extends BaseJpaEntity {
    
    @Column(name = "policy_key", nullable = false, length = 100, unique = true)
    private String policyKey;
    @Column(name = "policy_name", nullable = false, length = 255)
    private String policyName;
    @Column(name = "description")
    private String description;
    @Column(name = "default_effect", length = 50)
    private String defaultEffect;
    @Column(name = "owner_service", length = 100)
    private String ownerService;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
