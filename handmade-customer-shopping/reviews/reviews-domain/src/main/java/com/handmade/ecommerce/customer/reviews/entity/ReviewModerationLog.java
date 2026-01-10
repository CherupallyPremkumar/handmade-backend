package com.handmade.ecommerce.customer.reviews.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_review_moderation_log
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_review_moderation_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ReviewModerationLog extends BaseJpaEntity {
    
    @Column(name = "review_id", nullable = false, length = 36)
    private String reviewId;
    @Column(name = "moderator_id", nullable = false, length = 36)
    private String moderatorId;
    @Column(name = "action", nullable = false, length = 50)
    private String action;
    @Column(name = "reason", length = 255)
    private String reason;
    @Column(name = "notes")
    private String notes;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
