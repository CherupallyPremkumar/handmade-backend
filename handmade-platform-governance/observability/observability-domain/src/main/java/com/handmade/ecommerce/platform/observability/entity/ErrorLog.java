package com.handmade.ecommerce.platform.observability.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_error_log
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_error_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ErrorLog extends BaseJpaEntity {
    
    @Column(name = "service_name", nullable = false, length = 100)
    private String serviceName;
    @Column(name = "trace_id", length = 100)
    private String traceId;
    @Column(name = "span_id", length = 100)
    private String spanId;
    @Column(name = "error_message", nullable = false)
    private String errorMessage;
    @Column(name = "stack_trace")
    private String stackTrace;
    @Column(name = "severity", length = 20)
    private String severity;
    @Column(name = "environment", length = 20)
    private String environment;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
