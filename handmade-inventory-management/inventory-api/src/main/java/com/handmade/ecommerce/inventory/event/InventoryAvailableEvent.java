package com.handmade.ecommerce.inventory.event;

import lombok.Getter;
import lombok.ToString;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@ToString
public class InventoryAvailableEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String variantId;
    private final String sellerId;
    private final String locationId;
    private final int availableQuantity;
    private final LocalDateTime timestamp;

    public InventoryAvailableEvent(String variantId, String sellerId, String locationId, int availableQuantity) {
        this.variantId = variantId;
        this.sellerId = sellerId;
        this.locationId = locationId;
        this.availableQuantity = availableQuantity;
        this.timestamp = LocalDateTime.now();
    }
}
