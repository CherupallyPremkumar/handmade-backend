package com.handmade.ecommerce.platform.limits.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_limit_counter
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_limit_counter")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class LimitCounter extends BaseJpaEntity {
    
    @Column(name = "entity_id", nullable = false, length = 100)
    private String entityId;
    @Column(name = "limit_key", nullable = false, length = 100)
    private String limitKey;
    @Column(name = "current_usage")
    private Long currentUsage;
    @Column(name = "reset_time")
    private Date resetTime;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
