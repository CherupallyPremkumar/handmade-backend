package com.handmade.ecommerce.customer.cart.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_cart_saved_for_later
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_cart_saved_for_later")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CartSavedForLater extends BaseJpaEntity {
    
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "offer_id", nullable = false, length = 36)
    private String offerId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "product_name", length = 500)
    private String productName;
    @Column(name = "product_image_url", length = 2048)
    private String productImageUrl;
    @Column(name = "seller_id", length = 36)
    private String sellerId;
    @Column(name = "unit_price", precision = 19, scale = 4)
    private BigDecimal unitPrice;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "quantity")
    private String quantity;
    @Column(name = "saved_date", nullable = false)
    private Date savedDate;
    @Column(name = "notes")
    private String notes;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
