package com.handmade.ecommerce.domain.cart;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * CartItem - Items in shopping cart
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cart_item")
public class CartItem extends BaseJpaEntity {

    @Column(name = "cart_id", length = 36, nullable = false)
    private String cartId;

    @Column(name = "offer_id", length = 36, nullable = false)
    private String offerId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "product_name", length = 500)
    private String productName;

    @Column(name = "product_image_url", length = 2048)
    private String productImageUrl;

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "seller_name", length = 255)
    private String sellerName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "subtotal", precision = 19, scale = 4, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "discount_amount", precision = 19, scale = 4)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 19, scale = 4)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "USD";

    @Column(name = "applied_promotion_id", length = 36)
    private String appliedPromotionId;

    @Column(name = "applied_coupon_code", length = 100)
    private String appliedCouponCode;

    @Column(name = "is_gift")
    private Boolean isGift = false;

    @Lob
    @Column(name = "gift_message", columnDefinition = "TEXT")
    private String giftMessage;

    @Column(name = "gift_wrap_type", length = 50)
    private String giftWrapType;

    @Column(name = "availability_status", length = 50)
    private String availabilityStatus;

    @Column(name = "estimated_delivery_date")
    @Temporal(TemporalType.DATE)
    private java.util.Date estimatedDeliveryDate;
}
