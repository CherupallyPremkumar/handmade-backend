package com.handmade.ecommerce.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request to update cart item quantity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemRequest {
    
    /**
     * Cart line ID
     */
    private String cartLineId;
    
    /**
     * New quantity
     */
    private Integer quantity;
}
