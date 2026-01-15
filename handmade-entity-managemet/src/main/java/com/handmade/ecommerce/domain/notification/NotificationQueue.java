package com.handmade.ecommerce.domain.notification;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_notification_queue")
public class NotificationQueue extends AbstractJpaStateEntity {

    @Column(name = "recipient_id", length = 100, nullable = false)
    private String recipientId;

    @Column(name = "recipient_type", length = 50)
    private String recipientType; // USER, SELLER

    @Column(name = "template_id", length = 100)
    private String templateId;

    @Column(name = "channel", length = 20)
    private String channel; // EMAIL, SMS, PUSH

    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "status", length = 20)
    private String status = "PENDING";

    @Column(name = "retry_count")
    private Integer retryCount = 0;

    @Column(name = "scheduled_at")
    private Date scheduledAt;
}
