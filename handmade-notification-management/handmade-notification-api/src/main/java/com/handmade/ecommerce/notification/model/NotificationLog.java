package com.handmade.ecommerce.notification.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_notification_log")
public class NotificationLog extends AbstractJpaStateEntity {

    @Column(name = "user_id", length = 36, nullable = false)
    private String userId;

    @Column(name = "template_id", length = 36, nullable = false)
    private String templateId;

    @Column(name = "channel", length = 20, nullable = false)
    private String channel;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "provider_reference_id", length = 100)
    private String providerReferenceId;

    @Column(name = "sent_at", nullable = false)
    private Date sentAt;

    @Lob
    @Column(name = "error_message")
    private String errorMessage;
}
