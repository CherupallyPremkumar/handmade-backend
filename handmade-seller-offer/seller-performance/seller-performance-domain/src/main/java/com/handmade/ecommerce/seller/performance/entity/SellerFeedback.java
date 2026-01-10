package com.handmade.ecommerce.seller.performance.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_seller_feedback
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerFeedback extends BaseJpaEntity {
    
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "order_id", length = 36)
    private String orderId;
    @Column(name = "rating", nullable = false)
    private String rating;
    @Column(name = "comment", length = 1000)
    private String comment;
    @Column(name = "is_verified_purchase")
    private Boolean isVerifiedPurchase;
    @Column(name = "response_text", length = 1000)
    private String responseText;
    @Column(name = "response_date")
    private Date responseDate;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
