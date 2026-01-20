package com.handmade.ecommerce.notification.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_notification_template")
public class NotificationTemplate extends AbstractJpaStateEntity {

    @Column(name = "template_code", length = 100, nullable = false, unique = true)
    private String templateCode;

    @Column(name = "channel", length = 20, nullable = false)
    private String channel;

    @Column(name = "subject_template", length = 255)
    private String subjectTemplate;

    @Lob
    @Column(name = "body_template", nullable = false)
    private String bodyTemplate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
