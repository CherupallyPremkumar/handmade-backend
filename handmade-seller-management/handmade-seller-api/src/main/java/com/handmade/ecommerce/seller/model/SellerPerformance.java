package com.handmade.ecommerce.seller.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

import java.util.Date;

/**
 * SellerPerformance - Seller performance metrics and ratings
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_performance")
public class SellerPerformance extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "metric_name", length = 50)
    private String metricName;

    @Column(name = "metric_value", precision = 10, scale = 4)
    private BigDecimal metricValue;

    @Column(name = "target_value", precision = 10, scale = 4)
    private BigDecimal targetValue;

    @Column(name = "window_start_date")
    private Date windowStartDate;

    @Column(name = "window_end_date")
    private Date windowEndDate;

    @Column(name = "status", length = 50)
    private String status;
}
