package com.handmade.ecommerce.product.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event published when a product is approved and ready for public catalog.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductApprovedEvent {
    private String productId;
}
