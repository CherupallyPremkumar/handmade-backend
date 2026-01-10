package com.handmade.ecommerce.platform.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_user_preference
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_user_preference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserPreference extends BaseJpaEntity {
    
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;
    @Column(name = "notification_type", nullable = false, length = 50)
    private String notificationType;
    @Column(name = "channel", nullable = false, length = 20)
    private String channel;
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
