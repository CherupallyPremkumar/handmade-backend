package com.handmade.ecommerce.customer.qa.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_qa_vote
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_qa_vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class QaVote extends BaseJpaEntity {
    
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "question_id", length = 36)
    private String questionId;
    @Column(name = "answer_id", length = 36)
    private String answerId;
    @Column(name = "vote_type", nullable = false, length = 20)
    private String voteType;
    @Column(name = "voted_at", nullable = false)
    private Date votedAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
