package com.handmade.ecommerce.platform.observability.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_activity_stream
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_activity_stream")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ActivityStream extends BaseJpaEntity {
    
    @Column(name = "actor_id", length = 100)
    private String actorId;
    @Column(name = "actor_type", length = 50)
    private String actorType;
    @Column(name = "action", nullable = false, length = 100)
    private String action;
    @Column(name = "entity_id", length = 100)
    private String entityId;
    @Column(name = "entity_type", length = 50)
    private String entityType;
    @Column(name = "metadata")
    private String metadata;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
