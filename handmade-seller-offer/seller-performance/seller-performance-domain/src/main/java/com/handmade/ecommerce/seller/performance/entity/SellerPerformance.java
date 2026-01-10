package com.handmade.ecommerce.seller.performance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_seller_performance
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_performance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerPerformance extends BaseJpaEntity {
    
    @Column(name = "seller_id", nullable = false, length = 36)
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
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
