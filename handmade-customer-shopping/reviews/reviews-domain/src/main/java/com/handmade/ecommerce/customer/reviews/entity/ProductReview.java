package com.handmade.ecommerce.customer.reviews.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_product_review
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_product_review")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductReview extends BaseJpaEntity {
    
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "rating", nullable = false)
    private String rating;
    @Column(name = "title", length = 255)
    private String title;
    @Column(name = "review_text")
    private String reviewText;
    @Column(name = "is_verified_purchase")
    private Boolean isVerifiedPurchase;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "helpful_votes")
    private String helpfulVotes;
    @Column(name = "images_json")
    private String imagesJson;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
