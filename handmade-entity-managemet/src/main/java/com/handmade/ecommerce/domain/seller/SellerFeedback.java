package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SellerFeedback - Customer feedback for sellers
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_feedback")
public class SellerFeedback extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "order_id", length = 36)
    private String orderId;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "is_verified_purchase")
    private Boolean isVerifiedPurchase = false;

    @Column(name = "response_text", length = 1000)
    private String responseText;

    @Column(name = "response_date")
    private java.util.Date responseDate;
}
