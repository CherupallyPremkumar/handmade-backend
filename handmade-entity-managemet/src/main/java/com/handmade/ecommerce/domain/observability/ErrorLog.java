package com.handmade.ecommerce.domain.observability;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_error_log")
public class ErrorLog extends BaseJpaEntity {

    @Column(name = "service_name", length = 100, nullable = false)
    private String serviceName;

    @Column(name = "trace_id", length = 100)
    private String traceId;

    @Column(name = "span_id", length = 100)
    private String spanId;

    @Column(name = "error_message", columnDefinition = "TEXT", nullable = false)
    private String errorMessage;

    @Column(name = "stack_trace", columnDefinition = "TEXT")
    private String stackTrace;

    @Column(name = "severity", length = 20)
    private String severity = "ERROR";

    @Column(name = "environment", length = 20)
    private String environment;
}
