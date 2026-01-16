package com.handmade.ecommerce.domain.qa;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.util.Date;

/**
 * QAVote - Votes on questions and answers
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_qa_vote")
public class QAVote extends BaseJpaEntity {

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "question_id", length = 36)
    private String questionId;

    @Column(name = "answer_id", length = 36)
    private String answerId;

    @Column(name = "vote_type", length = 20, nullable = false)
    private String voteType; // UPVOTE, DOWNVOTE

    @Column(name = "voted_at", nullable = false)
    private Date votedAt;
}
