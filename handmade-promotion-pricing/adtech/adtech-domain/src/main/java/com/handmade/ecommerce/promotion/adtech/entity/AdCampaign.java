package com.handmade.ecommerce.promotion.adtech.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_ad_campaign
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_ad_campaign")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AdCampaign extends BaseJpaEntity {
    
    @Column(name = "platform_id", nullable = false, length = 36)
    private String platformId;
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "campaign_name", nullable = false, length = 100)
    private String campaignName;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "budget_type", nullable = false, length = 20)
    private String budgetType;
    @Column(name = "budget_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal budgetAmount;
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
