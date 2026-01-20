package com.handmade.ecommerce.reviews.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * ProductReviewVote - Helpful/Not Helpful votes on reviews
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_review_vote")
public class ProductReviewVote extends BaseJpaEntity {

    @Column(name = "review_id", length = 36, nullable = false)
    private String reviewId;

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "vote_type", length = 20, nullable = false)
    private String voteType; // HELPFUL, NOT_HELPFUL, REPORT
}
