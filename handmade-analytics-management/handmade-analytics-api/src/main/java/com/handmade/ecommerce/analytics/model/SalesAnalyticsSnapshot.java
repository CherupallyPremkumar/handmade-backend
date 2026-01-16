package com.handmade.ecommerce.analytics.model;

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
@Table(name = "hm_sales_analytics_snapshot")
public class SalesAnalyticsSnapshot extends BaseJpaEntity {

    @Column(name = "platform_id", length = 36, nullable = false)
    private String platformId;

    @Column(name = "dimension", length = 50)
    private String dimension;

    @Column(name = "dimension_value", length = 100)
    private String dimensionValue;

    @Column(name = "metric_name", length = 50)
    private String metricName;

    @Column(name = "metric_value", precision = 19, scale = 4)
    private BigDecimal metricValue;

    @Column(name = "date_day")
    @Temporal(TemporalType.DATE)
    private Date dateDay;
}
