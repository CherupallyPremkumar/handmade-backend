package com.handmade.ecommerce.customer.reviews.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_product_review_vote
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_product_review_vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductReviewVote extends BaseJpaEntity {
    
    @Column(name = "review_id", nullable = false, length = 36)
    private String reviewId;
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "vote_type", nullable = false, length = 20)
    private String voteType;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
