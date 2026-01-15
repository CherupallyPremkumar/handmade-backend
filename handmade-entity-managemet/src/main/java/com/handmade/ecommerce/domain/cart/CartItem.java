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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cart_item")
public class CartItem extends BaseJpaEntity {

    @Column(name = "cart_id", length = 36, nullable = false)
    private String cartId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "offer_id", length = 36)
    private String offerId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price_at_addition", precision = 19, scale = 4)
    private BigDecimal priceAtAddition;
}
