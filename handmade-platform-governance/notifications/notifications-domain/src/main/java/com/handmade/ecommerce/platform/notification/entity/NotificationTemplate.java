package com.handmade.ecommerce.platform.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_notification_template
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_notification_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class NotificationTemplate extends BaseJpaEntity {
    
    @Column(name = "template_code", nullable = false, length = 100, unique = true)
    private String templateCode;
    @Column(name = "channel", nullable = false, length = 20)
    private String channel;
    @Column(name = "subject_template", length = 255)
    private String subjectTemplate;
    @Column(name = "body_template", nullable = false)
    private String bodyTemplate;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
