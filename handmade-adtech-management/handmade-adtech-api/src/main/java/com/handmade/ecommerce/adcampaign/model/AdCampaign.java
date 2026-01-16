package com.handmade.ecommerce.adcampaign.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * AdCampaign - Advertising campaign configuration
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_ad_campaign")
public class AdCampaign extends AbstractJpaStateEntity {

    @Column(name = "platform_id", length = 36, nullable = false)
    private String platformId;

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "campaign_name", length = 100, nullable = false)
    private String campaignName;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "budget_type", length = 20, nullable = false)
    private String budgetType = "DAILY"; // DAILY, LIFETIME

    @Column(name = "budget_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal budgetAmount;

}
