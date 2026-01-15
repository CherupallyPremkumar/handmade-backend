package com.handmade.ecommerce.domain.event;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_event_queue")
public class EventQueue extends AbstractJpaStateEntity {

    @Column(name = "event_type", length = 100, nullable = false)
    private String eventType;

    @Column(name = "payload", columnDefinition = "TEXT", nullable = false)
    private String payload;

    @Column(name = "status", length = 20)
    private String status = "PENDING";

    @Column(name = "retry_count")
    private Integer retryCount = 0;

    @Column(name = "next_retry_time")
    private Date nextRetryTime;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
}
