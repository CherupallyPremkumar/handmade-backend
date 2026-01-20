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
@Table(name = "hm_ad_performance_daily")
public class AdPerformanceDaily extends BaseJpaEntity {

    @Column(name = "campaign_id", length = 36, nullable = false)
    private String campaignId;

    @Column(name = "impressions")
    private Long impressions = 0L;

    @Column(name = "clicks")
    private Long clicks = 0L;

    @Column(name = "conversions")
    private Long conversions = 0L;

    @Column(name = "spend", precision = 19, scale = 4)
    private BigDecimal spend;

    @Column(name = "sales", precision = 19, scale = 4)
    private BigDecimal sales;

    @Column(name = "date_day")
    @Temporal(TemporalType.DATE)
    private Date dateDay;
}
