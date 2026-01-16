package com.handmade.ecommerce.reviews.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * ReviewModerationLog - Audit log for review moderation actions
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_review_moderation_log")
public class ReviewModerationLog extends BaseJpaEntity {

    @Column(name = "review_id", length = 36, nullable = false)
    private String reviewId;

    @Column(name = "moderator_id", length = 36, nullable = false)
    private String moderatorId;

    @Column(name = "action", length = 50, nullable = false)
    private String action; // APPROVE, REJECT, REQUEST_EDIT

    @Column(name = "reason")
    private String reason;

    @Lob
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
