package com.handmade.ecommerce.cart.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * CartSavedForLater - Items saved for later purchase
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cart_saved_for_later")
public class CartSavedForLater extends BaseJpaEntity {

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "offer_id", length = 36, nullable = false)
    private String offerId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "product_name", length = 500)
    private String productName;

    @Column(name = "product_image_url", length = 2048)
    private String productImageUrl;

    @Column(name = "seller_id", length = 36)
    private String sellerId;

    @Column(name = "unit_price", precision = 19, scale = 4)
    private java.math.BigDecimal unitPrice;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "USD";

    @Column(name = "quantity")
    private Integer quantity = 1;

    @Column(name = "saved_date", nullable = false)
    private java.util.Date savedDate;

    @Lob
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
