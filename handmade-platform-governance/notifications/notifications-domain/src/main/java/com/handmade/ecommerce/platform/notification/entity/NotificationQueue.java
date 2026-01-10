package com.handmade.ecommerce.platform.notification.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_notification_queue
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_notification_queue")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class NotificationQueue extends BaseJpaEntity {
    
    @Column(name = "recipient_id", nullable = false, length = 100)
    private String recipientId;
    @Column(name = "recipient_type", length = 50)
    private String recipientType;
    @Column(name = "template_id", length = 100)
    private String templateId;
    @Column(name = "channel", length = 20)
    private String channel;
    @Column(name = "payload")
    private String payload;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "retry_count")
    private String retryCount;
    @Column(name = "scheduled_at")
    private Date scheduledAt;
    @Column(name = "created_at")
    private Date createdAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
