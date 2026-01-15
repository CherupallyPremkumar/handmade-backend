package com.handmade.ecommerce.domain.reviews;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * ProductReview - Customer reviews for products
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_review")
public class ProductReview extends AbstractJpaStateEntity {

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "rating", nullable = false)
    private Integer rating; // 1-5

    @Column(name = "title")
    private String title;

    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;

    @Column(name = "is_verified_purchase")
    private Boolean isVerifiedPurchase = false;

    @Column(name = "status", length = 20)
    private String status; // Managed by STM

    @Column(name = "helpful_votes")
    private Integer helpfulVotes = 0;

    @Column(name = "images_json", columnDefinition = "TEXT")
    private String imagesJson;
}
