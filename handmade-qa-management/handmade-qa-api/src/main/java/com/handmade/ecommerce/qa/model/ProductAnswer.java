package com.handmade.ecommerce.qa.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * ProductAnswer - Answers to product questions
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_answer")
public class ProductAnswer extends AbstractJpaStateEntity {

    @Column(name = "question_id", length = 36, nullable = false)
    private String questionId;

    @Column(name = "seller_id", length = 36)
    private String sellerId;

    @Column(name = "customer_id", length = 36)
    private String customerId;

    @Lob
    @Column(name = "answer_text", nullable = false)
    private String answerText;

    @Column(name = "is_seller_answer")
    private Boolean isSellerAnswer = false;

    // Status managed by STM (PENDING, APPROVED, REJECTED)

    @Column(name = "upvote_count")
    private Integer upvoteCount = 0;

    @Column(name = "downvote_count")
    private Integer downvoteCount = 0;

    @Column(name = "moderated_by")
    private String moderatedBy;

    @Column(name = "moderated_at")
    private Date moderatedAt;
}
