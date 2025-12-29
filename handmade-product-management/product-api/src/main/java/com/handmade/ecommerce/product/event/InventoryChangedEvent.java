package com.handmade.ecommerce.product.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event published when product inventory changes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryChangedEvent {
    private String productId;
    private int newQuantity;
}
