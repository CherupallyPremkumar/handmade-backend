package com.handmade.ecommerce.catalog.service.listener;

import com.handmade.ecommerce.product.event.InventoryChangedEvent;
import com.handmade.ecommerce.product.event.ProductApprovedEvent;
import com.handmade.ecommerce.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ProductEventListener {

    @Autowired
    private CatalogService catalogService;

    @EventListener
    public void onProductApproved(ProductApprovedEvent event) {
        // "Upsert" logic to handle duplicate events
        // Using idempotent service method
        if (event.getProductId() != null) {
            catalogService.createOrUpdateCatalogItem(event.getProductId());
        }
    }

    @EventListener
    public void onProductInventoryChanged(InventoryChangedEvent event) {
        if (event.getProductId() != null) {
            catalogService.updateVisibility(event.getProductId(), event.getNewQuantity());
        }
    }
}
