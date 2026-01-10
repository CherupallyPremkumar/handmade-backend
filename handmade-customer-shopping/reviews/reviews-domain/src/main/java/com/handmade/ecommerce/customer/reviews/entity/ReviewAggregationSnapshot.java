package com.handmade.ecommerce.customer.reviews.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_review_aggregation_snapshot
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_review_aggregation_snapshot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ReviewAggregationSnapshot extends BaseJpaEntity {
    
    @Column(name = "product_id", nullable = false, length = 36, unique = true)
    private String productId;
    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating;
    @Column(name = "total_reviews")
    private String totalReviews;
    @Column(name = "rating_distribution_json")
    private String ratingDistributionJson;
    @Column(name = "last_calculated_at")
    private Date lastCalculatedAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
