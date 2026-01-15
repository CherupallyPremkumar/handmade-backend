package com.handmade.ecommerce.domain.qa;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * QAVote - Votes on questions and answers
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_qa_vote")
public class QAVote extends BaseJpaEntity {

    @Column(name = "entity_id", length = 36, nullable = false)
    private String entityId; // Question ID or Answer ID

    @Column(name = "entity_type", length = 50, nullable = false)
    private String entityType; // QUESTION, ANSWER

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "vote_type", length = 20, nullable = false)
    private String voteType; // UPVOTE, DOWNVOTE
}
