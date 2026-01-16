package com.handmade.ecommerce.domain.analytics;

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
@Table(name = "hm_order_summary")
public class OrderSummary extends BaseJpaEntity {

    @Column(name = "summary_type", length = 20)
    private String summaryType;

    @Column(name = "summary_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date summaryDate;

    @Column(name = "region_code", length = 2)
    private String regionCode;

    @Column(name = "total_orders")
    private Long totalOrders;

    @Column(name = "total_revenue", precision = 19, scale = 4)
    private BigDecimal totalRevenue;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "last_updated")
    private Date lastUpdated;
}
