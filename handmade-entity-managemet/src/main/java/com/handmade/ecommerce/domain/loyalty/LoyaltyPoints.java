package com.handmade.ecommerce.domain.loyalty;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_loyalty_points")
public class LoyaltyPoints extends BaseJpaEntity {

    @Column(name = "customer_id", length = 36, nullable = false, unique = true)
    private String customerId;

    @Column(name = "current_balance")
    private Long currentBalance = 0L;

    @Column(name = "total_earned")
    private Long totalEarned = 0L;

    @Column(name = "total_redeemed")
    private Long totalRedeemed = 0L;

    @Column(name = "last_updated")
    private Date lastUpdated;
}
