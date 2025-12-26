package com.handmade.ecommerce.catalog.service;

import com.handmade.ecommerce.catalog.model.CatalogItem;

public interface CatalogService {
    /**
     * Create or update a catalog item for a given product ID.
     * This method is idempotent and safe to call multiple times.
     * 
     * @param productId the unique identifier of the product
     * @return the created or updated CatalogItem
     */
    CatalogItem createOrUpdateCatalogItem(String productId);

    /**
     * Update the visibility of a catalog item based on inventory quantity.
     * 
     * @param productId   the unique identifier of the product
     * @param newQuantity the new available quantity
     */
    void updateVisibility(String productId, int newQuantity);
}
