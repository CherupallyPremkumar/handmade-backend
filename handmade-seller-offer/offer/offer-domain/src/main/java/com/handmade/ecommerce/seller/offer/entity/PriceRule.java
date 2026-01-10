package com.handmade.ecommerce.seller.offer.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_price_rule
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_price_rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PriceRule extends BaseJpaEntity {
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "rule_type", nullable = false, length = 50)
    private String ruleType;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "min_price_limit", precision = 10, scale = 2)
    private BigDecimal minPriceLimit;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
