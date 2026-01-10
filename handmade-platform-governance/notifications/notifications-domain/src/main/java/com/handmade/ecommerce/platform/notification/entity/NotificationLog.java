package com.handmade.ecommerce.platform.notification.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_notification_log
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_notification_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class NotificationLog extends BaseJpaEntity {
    
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;
    @Column(name = "template_id", nullable = false, length = 36)
    private String templateId;
    @Column(name = "channel", nullable = false, length = 20)
    private String channel;
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    @Column(name = "provider_reference_id", length = 100)
    private String providerReferenceId;
    @Column(name = "sent_at", nullable = false)
    private Date sentAt;
    @Column(name = "error_message")
    private String errorMessage;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
