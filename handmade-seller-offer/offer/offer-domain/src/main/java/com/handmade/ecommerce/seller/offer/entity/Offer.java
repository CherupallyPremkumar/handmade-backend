package com.handmade.ecommerce.seller.offer.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * JPA Entity for hm_offer
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_offer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Offer extends AbstractJpaStateEntity {
    
    @Column(name = "platform_id", nullable = false, length = 36)
    private String platformId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "seller_sku", nullable = false, length = 100)
    private String sellerSku;
    @Column(name = "condition_type", length = 50)
    private String conditionType;
    @Column(name = "condition_note", length = 2000)
    private String conditionNote;
    @Column(name = "fulfillment_channel", length = 50)
    private String fulfillmentChannel;
    @Column(name = "available_quantity")
    private String availableQuantity;
    @Column(name = "restock_date")
    private Date restockDate;
    @Column(name = "max_order_quantity")
    private String maxOrderQuantity;
    @Column(name = "tax_code", length = 50)
    private String taxCode;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
