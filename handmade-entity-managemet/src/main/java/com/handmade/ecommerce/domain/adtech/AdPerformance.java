package com.handmade.ecommerce.domain.adtech;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * AdPerformance - Daily performance metrics for sponsored products
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_ad_performance")
public class AdPerformance extends BaseJpaEntity {

    @Column(name = "sponsored_product_id", length = 36, nullable = false)
    private String sponsoredProductId;

    @Column(name = "report_date", nullable = false)
    private Date reportDate;

    @Column(name = "impressions", nullable = false)
    private Long impressions = 0L;

    @Column(name = "clicks", nullable = false)
    private Long clicks = 0L;

    @Column(name = "spend", precision = 10, scale = 2, nullable = false)
    private BigDecimal spend;

    @Column(name = "sales", precision = 10, scale = 2, nullable = false)
    private BigDecimal sales;
}
