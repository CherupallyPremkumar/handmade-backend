package com.handmade.ecommerce.promotion.loyalty.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_loyalty_points
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_loyalty_points")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class LoyaltyPoints extends BaseJpaEntity {
    
    @Column(name = "customer_id", nullable = false, length = 36, unique = true)
    private String customerId;
    @Column(name = "current_balance")
    private Long currentBalance;
    @Column(name = "total_earned")
    private Long totalEarned;
    @Column(name = "total_redeemed")
    private Long totalRedeemed;
    @Column(name = "last_updated")
    private Date lastUpdated;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
