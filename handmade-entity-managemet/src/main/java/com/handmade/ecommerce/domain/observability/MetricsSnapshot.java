package com.handmade.ecommerce.domain.observability;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_metrics_snapshot")
public class MetricsSnapshot extends BaseJpaEntity {

    @Column(name = "metric_name", length = 100, nullable = false)
    private String metricName;

    @Column(name = "metric_value", precision = 19, scale = 4, nullable = false)
    private BigDecimal metricValue;

    @Column(name = "unit", length = 20)
    private String unit;

    @Lob
    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "captured_at")
    private Date capturedAt;
}
