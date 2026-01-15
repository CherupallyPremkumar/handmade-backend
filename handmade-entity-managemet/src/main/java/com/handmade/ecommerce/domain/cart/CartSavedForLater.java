package com.handmade.ecommerce.domain.cart;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * CartSavedForLater - Items saved for later purchase
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cart_saved_for_later")
public class CartSavedForLater extends BaseJpaEntity {

    @Column(name = "cart_id", length = 36, nullable = false)
    private String cartId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
