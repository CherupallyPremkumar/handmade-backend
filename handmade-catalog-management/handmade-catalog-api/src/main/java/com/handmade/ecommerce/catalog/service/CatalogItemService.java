package com.handmade.ecommerce.catalog.service;


import com.handmade.ecommerce.catalog.model.CatalogItem;

public interface CatalogItemService {
	// Define your interface here
    public CatalogItem save(CatalogItem catalog);
    public CatalogItem retrieve(String id);
}
