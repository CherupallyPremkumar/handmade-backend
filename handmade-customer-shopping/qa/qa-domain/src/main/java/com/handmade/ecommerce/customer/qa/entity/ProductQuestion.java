package com.handmade.ecommerce.customer.qa.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_product_question
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_product_question")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductQuestion extends BaseJpaEntity {
    
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "customer_name", length = 255)
    private String customerName;
    @Column(name = "question_text", nullable = false)
    private String questionText;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "answer_count")
    private String answerCount;
    @Column(name = "helpful_votes")
    private String helpfulVotes;
    @Column(name = "is_answered")
    private Boolean isAnswered;
    @Column(name = "asked_at", nullable = false)
    private Date askedAt;
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
