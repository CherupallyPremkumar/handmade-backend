package com.handmade.ecommerce.seller.messaging.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_seller_conversation
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_conversation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerConversation extends BaseJpaEntity {
    
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "product_id", length = 36)
    private String productId;
    @Column(name = "order_id", length = 36)
    private String orderId;
    @Column(name = "subject", length = 500)
    private String subject;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "message_count")
    private String messageCount;
    @Column(name = "last_message_at")
    private Date lastMessageAt;
    @Column(name = "closed_at")
    private Date closedAt;
    @Column(name = "closed_by", length = 255)
    private String closedBy;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
