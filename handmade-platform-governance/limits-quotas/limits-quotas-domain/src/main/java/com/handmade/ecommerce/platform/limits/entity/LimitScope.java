package com.handmade.ecommerce.platform.limits.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_limit_scope
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_limit_scope")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class LimitScope extends BaseJpaEntity {
    
    @Column(name = "limit_id", nullable = false, length = 36)
    private String limitId;
    @Column(name = "scope_json", nullable = false)
    private String scopeJson;
    @Column(name = "override_value", nullable = false)
    private Long overrideValue;
    @Column(name = "priority")
    private String priority;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
