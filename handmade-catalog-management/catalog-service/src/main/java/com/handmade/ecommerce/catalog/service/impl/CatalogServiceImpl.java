package com.handmade.ecommerce.catalog.service.impl;

import com.handmade.ecommerce.catalog.model.CatalogItem;
import com.handmade.ecommerce.catalog.service.CatalogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final com.handmade.ecommerce.catalog.repository.CatalogItemRepository catalogItemRepository;
    private final com.handmade.ecommerce.catalog.service.integration.ProductServiceClient productServiceClient;

    @org.springframework.beans.factory.annotation.Autowired
    public CatalogServiceImpl(
            com.handmade.ecommerce.catalog.repository.CatalogItemRepository catalogItemRepository,
            com.handmade.ecommerce.catalog.service.integration.ProductServiceClient productServiceClient) {
        this.catalogItemRepository = catalogItemRepository;
        this.productServiceClient = productServiceClient;
    }

    @Override
    @Transactional
    public CatalogItem createOrUpdateCatalogItem(String productId) {
        // 1. Fetch fresh product data from ACL
        com.handmade.ecommerce.catalog.service.integration.ExternalProductDto product = 
            productServiceClient.getProduct(productId)
                .orElseThrow(() -> new org.chenile.base.exception.NotFoundException("Product not found via ACL: " + productId));

        // 2. Fetch existing CatalogItem or create new
        java.util.Optional<CatalogItem> existingItemOpt = catalogItemRepository.findByProductId(productId);
        CatalogItem catalogItem;

        if (existingItemOpt.isPresent()) {
            // Update Path: Preserve merchandising data
            catalogItem = existingItemOpt.get();
        } else {
            // Create Path: Set defaults
            catalogItem = new CatalogItem();
            catalogItem.setProductId(productId);
            catalogItem.setFeatured(false);
            catalogItem.setDisplayOrder(999); // Default to end of list
            catalogItem.setActive(true);
        }

        // 3. Update Critical Fields (CQRS Projection Pattern)
        // We ALWAYS update these to match the Source of Truth (Product Management)
        catalogItem.setName(product.getName());
        catalogItem.setPrice(product.getPrice());
        
        // 4. Update Visibility Logic (Basic)
        // If product is inactive in source, it must be hidden in catalog
        // But if active in source, we respect the CatalogItem's own active flag (merchandising choice)
        if (!product.isActive()) {
            catalogItem.setActive(false);
        }

        return catalogItemRepository.save(catalogItem);
    }

    @Override
    @Transactional
    public void updateVisibility(String productId, int newQuantity) {
        java.util.Optional<CatalogItem> itemOpt = catalogItemRepository.findByProductId(productId);
        if (itemOpt.isPresent()) {
            CatalogItem item = itemOpt.get();
            // If out of stock, hide from catalog (Business Rule)
            // But if back in stock, we might want to unhide OR strictly follow merchandising rules
            // Implementation choice: Only auto-hide on stockout. Manual unhide required? 
            // Or auto-unhide? Let's assume auto-unhide for now if it was active before.
            // For simplicity in this demo:
            if (newQuantity <= 0) {
                 item.setActive(false);
            }
            // More complex logic would need a "stockStatus" field distinct from "merchandisingActive"
            catalogItemRepository.save(item);
        }
    }
}
