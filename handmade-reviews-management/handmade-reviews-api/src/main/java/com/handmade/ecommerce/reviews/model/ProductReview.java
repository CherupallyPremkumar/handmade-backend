package com.handmade.ecommerce.reviews.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * ProductReview - Customer reviews for products
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
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

    @Lob
    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "is_verified_purchase")
    private Boolean isVerifiedPurchase = false;

    @Column(name = "helpful_votes")
    private Integer helpfulVotes = 0;

    @Lob
    @Column(name = "images_json")
    private String imagesJson;
}
