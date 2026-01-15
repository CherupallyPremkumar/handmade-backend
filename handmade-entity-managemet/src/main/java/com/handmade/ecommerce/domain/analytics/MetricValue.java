package com.handmade.ecommerce.domain.analytics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_analytics_metric_value")
public class MetricValue extends BaseJpaEntity {

    @Column(name = "metric_def_id", length = 36, nullable = false)
    private String metricDefId;

    @Column(name = "dimension_key", length = 255)
    private String dimensionKey;

    @Column(name = "time_bucket", nullable = false)
    private Date timeBucket;

    @Column(name = "bucket_type", length = 20)
    private String bucketType; // HOUR, DAY, MONTH

    @Column(name = "metric_value", precision = 19, scale = 4)
    private BigDecimal metricValue;

    @Column(name = "sample_count")
    private Integer sampleCount = 0;
}
