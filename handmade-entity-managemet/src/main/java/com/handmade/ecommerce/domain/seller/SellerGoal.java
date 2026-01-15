package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * SellerGoal - Seller performance goals and targets
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_goal")
public class SellerGoal extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "goal_type", length = 50, nullable = false)
    private String goalType;

    @Column(name = "target_value", length = 255)
    private String targetValue;

    @Column(name = "current_value", length = 255)
    private String currentValue;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status", length = 50)
    private String status;
}
