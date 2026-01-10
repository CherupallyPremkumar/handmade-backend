package com.handmade.ecommerce.platform.observability.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_metrics_snapshot
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_metrics_snapshot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class MetricsSnapshot extends BaseJpaEntity {
    
    @Column(name = "metric_name", nullable = false, length = 100)
    private String metricName;
    @Column(name = "metric_value", nullable = false, precision = 19, scale = 4)
    private BigDecimal metricValue;
    @Column(name = "unit", length = 20)
    private String unit;
    @Column(name = "dimensions")
    private String dimensions;
    @Column(name = "captured_at")
    private Date capturedAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
