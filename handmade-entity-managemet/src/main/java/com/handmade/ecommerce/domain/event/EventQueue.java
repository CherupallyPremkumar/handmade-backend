package com.handmade.ecommerce.domain.event;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_event_queue")
public class EventQueue extends AbstractJpaStateEntity {

    @Column(name = "event_type", length = 100, nullable = false)
    private String eventType;

    @Lob
    @Column(name = "payload", nullable = false)
    private String payload;

    @Column(name = "retry_count")
    private Integer retryCount = 0;

    @Column(name = "next_retry_time")
    private Date nextRetryTime;

    @Lob
    @Column(name = "error_message")
    private String errorMessage;
}
