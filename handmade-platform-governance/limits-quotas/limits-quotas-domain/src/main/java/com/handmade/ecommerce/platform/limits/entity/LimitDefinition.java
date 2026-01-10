package com.handmade.ecommerce.platform.limits.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_limit_definition
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_limit_definition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class LimitDefinition extends BaseJpaEntity {
    
    @Column(name = "limit_key", nullable = false, length = 100, unique = true)
    private String limitKey;
    @Column(name = "resource_type", nullable = false, length = 50)
    private String resourceType;
    @Column(name = "default_value")
    private Long defaultValue;
    @Column(name = "description")
    private String description;
    @Column(name = "reset_period", length = 20)
    private String resetPeriod;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
