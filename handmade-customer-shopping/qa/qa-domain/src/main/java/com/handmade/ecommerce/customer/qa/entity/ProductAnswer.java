package com.handmade.ecommerce.customer.qa.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_product_answer
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_product_answer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductAnswer extends BaseJpaEntity {
    
    @Column(name = "question_id", nullable = false, length = 36)
    private String questionId;
    @Column(name = "customer_id", length = 36)
    private String customerId;
    @Column(name = "customer_name", length = 255)
    private String customerName;
    @Column(name = "seller_id", length = 36)
    private String sellerId;
    @Column(name = "answer_text", nullable = false)
    private String answerText;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "helpful_votes")
    private String helpfulVotes;
    @Column(name = "unhelpful_votes")
    private String unhelpfulVotes;
    @Column(name = "is_seller_answer")
    private Boolean isSellerAnswer;
    @Column(name = "is_verified_purchase")
    private Boolean isVerifiedPurchase;
    @Column(name = "answered_at", nullable = false)
    private Date answeredAt;
    @Column(name = "moderation_status", length = 50)
    private String moderationStatus;
    @Column(name = "moderated_at")
    private Date moderatedAt;
    @Column(name = "moderated_by", length = 255)
    private String moderatedBy;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
