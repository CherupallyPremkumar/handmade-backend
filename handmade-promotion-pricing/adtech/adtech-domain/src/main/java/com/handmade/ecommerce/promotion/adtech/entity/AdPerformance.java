package com.handmade.ecommerce.promotion.adtech.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_ad_performance
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_ad_performance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AdPerformance extends BaseJpaEntity {
    
    @Column(name = "sponsored_product_id", nullable = false, length = 36)
    private String sponsoredProductId;
    @Column(name = "report_date", nullable = false)
    private Date reportDate;
    @Column(name = "impressions", nullable = false)
    private Long impressions;
    @Column(name = "clicks", nullable = false)
    private Long clicks;
    @Column(name = "spend", nullable = false, precision = 10, scale = 2)
    private BigDecimal spend;
    @Column(name = "sales", nullable = false, precision = 10, scale = 2)
    private BigDecimal sales;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
