package com.handmade.ecommerce.qa.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * ProductQuestion - Customer questions about products
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_question")
public class ProductQuestion extends AbstractJpaStateEntity {

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Lob
    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "is_public")
    private Boolean isPublic = true;

    // Status managed by STM (PENDING, APPROVED, REJECTED, CLOSED)

    @Column(name = "upvote_count")
    private Integer upvoteCount = 0;

    @Column(name = "answer_count")
    private Integer answerCount = 0;

    @Column(name = "moderated_by")
    private String moderatedBy;

    @Column(name = "moderated_at")
    private Date moderatedAt;
}
