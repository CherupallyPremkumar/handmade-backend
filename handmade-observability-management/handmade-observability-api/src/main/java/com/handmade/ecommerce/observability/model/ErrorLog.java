package com.handmade.ecommerce.observability.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
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

    @Lob
    @Column(name = "error_message", nullable = false)
    private String errorMessage;

    @Lob
    @Column(name = "stack_trace")
    private String stackTrace;

    @Column(name = "severity", length = 20)
    private String severity = "ERROR";

    @Column(name = "environment", length = 20)
    private String environment;
}
