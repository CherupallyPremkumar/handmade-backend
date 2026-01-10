package com.handmade.ecommerce.customer.cart.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_cart_item
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_cart_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CartItem extends BaseJpaEntity {
    
    @Column(name = "cart_id", nullable = false, length = 36)
    private String cartId;
    @Column(name = "offer_id", nullable = false, length = 36)
    private String offerId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "product_name", length = 500)
    private String productName;
    @Column(name = "product_image_url", length = 2048)
    private String productImageUrl;
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "seller_name", length = 255)
    private String sellerName;
    @Column(name = "quantity", nullable = false)
    private String quantity;
    @Column(name = "unit_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal unitPrice;
    @Column(name = "subtotal", nullable = false, precision = 19, scale = 4)
    private BigDecimal subtotal;
    @Column(name = "discount_amount", precision = 19, scale = 4)
    private BigDecimal discountAmount;
    @Column(name = "tax_amount", precision = 19, scale = 4)
    private BigDecimal taxAmount;
    @Column(name = "total_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "applied_promotion_id", length = 36)
    private String appliedPromotionId;
    @Column(name = "applied_coupon_code", length = 100)
    private String appliedCouponCode;
    @Column(name = "is_gift")
    private Boolean isGift;
    @Column(name = "gift_message")
    private String giftMessage;
    @Column(name = "gift_wrap_type", length = 50)
    private String giftWrapType;
    @Column(name = "availability_status", length = 50)
    private String availabilityStatus;
    @Column(name = "estimated_delivery_date")
    private Date estimatedDeliveryDate;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
