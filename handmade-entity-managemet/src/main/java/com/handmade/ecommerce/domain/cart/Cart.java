package com.handmade.ecommerce.domain.cart;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * Cart - Shopping cart managed by STM (ACTIVE → CHECKED_OUT → ABANDONED)
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cart")
public class Cart extends AbstractJpaStateEntity {

    @Column(name = "customer_id", length = 36)
    private String customerId;

    @Column(name = "session_id", length = 255)
    private String sessionId;

    @Column(name = "subtotal_amount", precision = 19, scale = 4)
    private java.math.BigDecimal subtotalAmount = java.math.BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 19, scale = 4)
    private java.math.BigDecimal taxAmount = java.math.BigDecimal.ZERO;

    @Column(name = "shipping_amount", precision = 19, scale = 4)
    private java.math.BigDecimal shippingAmount = java.math.BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 19, scale = 4)
    private java.math.BigDecimal discountAmount = java.math.BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 19, scale = 4)
    private java.math.BigDecimal totalAmount = java.math.BigDecimal.ZERO;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "USD";

    @Column(name = "cart_type", length = 50)
    private String cartType = "STANDARD";

    @Column(name = "merged_from_cart_id", length = 36)
    private String mergedFromCartId;

    @Column(name = "expires_at")
    private Date expiresAt;

    @Column(name = "converted_to_order_id", length = 36)
    private String convertedToOrderId;

    @Column(name = "conversion_date")
    private Date conversionDate;
}
