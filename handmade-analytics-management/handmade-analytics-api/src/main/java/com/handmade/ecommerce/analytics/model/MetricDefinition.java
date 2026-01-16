package com.handmade.ecommerce.analytics.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_analytics_metric_def")
public class MetricDefinition extends AbstractJpaStateEntity {

    @Column(name = "metric_code", length = 100, nullable = false, unique = true)
    private String metricCode;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "aggregation_type", length = 20)
    private String aggregationType; // COUNT, SUM, AVG, MAX, MIN

    @Column(name = "value_type", length = 20)
    private String valueType; // INT, DECIMAL
}
