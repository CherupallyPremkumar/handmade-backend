package com.handmade.ecommerce.adcampaign.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * SponsoredProduct - Link between a product and a campaign
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_sponsored_product")
public class SponsoredProduct extends BaseJpaEntity {

    @Column(name = "campaign_id", length = 36, nullable = false)
    private String campaignId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "bid_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal bidAmount;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "ACTIVE";
}
