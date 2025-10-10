package com.homebase.admin.observer;

import com.homebase.admin.entity.TenantContext;
import com.homebase.admin.observer.event.ProductPriceChangedEvent;
import com.homebase.admin.service.CartService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Observer Pattern Implementation
 * Listens for ProductPriceChangedEvent and updates all affected cart items
 * This ensures cart prices are always current when product prices or sale status changes
 */
@Component
public class ProductPriceObserver {

    private final CartService cartService;

    public ProductPriceObserver(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Event handler for product price changes
     * Automatically recalculates cart item prices when:
     * - Product regular price changes
     * - Product goes on sale
     * - Product sale ends
     * - Sale price changes
     */
    @EventListener
    public void onProductPriceUpdated(ProductPriceChangedEvent event) {
        String tenantId = TenantContext.getCurrentTenant();
        
        System.out.println(String.format(
            "Price change detected for product %d: %s -> %s (Sale: %s -> %s)",
            event.getProductId(),
            event.getEffectiveOldPrice(),
            event.getEffectiveNewPrice(),
            event.getOldOnSale(),
            event.getNewOnSale()
        ));
        
        // Recalculate all cart items containing this product
        cartService.recalculateCartItemPrices(event.getProductId(), tenantId);
    }
}
