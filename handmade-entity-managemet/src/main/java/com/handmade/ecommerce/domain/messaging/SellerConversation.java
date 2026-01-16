package com.handmade.ecommerce.domain.messaging;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * SellerConversation - Manages communication threads between buyers and sellers
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_conversation")
public class SellerConversation extends AbstractJpaStateEntity {

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "product_id", length = 36)
    private String productId;

    @Column(name = "order_id", length = 36)
    private String orderId;

    @Column(name = "subject", length = 500)
    private String subject;

    @Column(name = "message_count")
    private Integer messageCount = 0;

    @Column(name = "last_message_at")
    private Date lastMessageAt;

    @Column(name = "closed_at")
    private Date closedAt;

    @Column(name = "closed_by", length = 255)
    private String closedBy;
}
