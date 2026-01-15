package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * SellerPerformance - Seller performance metrics and ratings
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_performance")
public class SellerPerformance extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false, unique = true)
    private String sellerId;

    @Column(name = "overall_rating", precision = 3, scale = 2)
    private BigDecimal overallRating;

    @Column(name = "total_reviews")
    private Long totalReviews = 0L;

    @Column(name = "order_fulfillment_rate", precision = 5, scale = 2)
    private BigDecimal orderFulfillmentRate;

    @Column(name = "on_time_delivery_rate", precision = 5, scale = 2)
    private BigDecimal onTimeDeliveryRate;

    @Column(name = "cancellation_rate", precision = 5, scale = 2)
    private BigDecimal cancellationRate;

    @Column(name = "defect_rate", precision = 5, scale = 2)
    private BigDecimal defectRate;
}
