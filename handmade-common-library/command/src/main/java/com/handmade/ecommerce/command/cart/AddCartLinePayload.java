package com.handmade.ecommerce.command.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request to add item to cart (Amazon-style)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCartLinePayload {

    private String cartId;
    private String variantId;

    private String sellerId;

    private Integer quantity;

}
