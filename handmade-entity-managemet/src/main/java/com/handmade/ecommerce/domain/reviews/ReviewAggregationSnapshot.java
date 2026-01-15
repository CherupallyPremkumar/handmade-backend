package com.handmade.ecommerce.domain.reviews;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ReviewAggregationSnapshot - Pre-calculated review stats for products
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_review_aggregation_snapshot")
public class ReviewAggregationSnapshot extends BaseJpaEntity {

    @Column(name = "product_id", length = 36, nullable = false, unique = true)
    private String productId;

    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating;

    @Column(name = "total_reviews")
    private Integer totalReviews = 0;

    @Column(name = "rating_distribution_json", columnDefinition = "TEXT")
    private String ratingDistributionJson;

    @Column(name = "last_calculated_at")
    private Date lastCalculatedAt;
}
