package com.handmade.ecommerce.catalog.service;

import com.handmade.ecommerce.catalog.model.Catalog;

public interface CatalogService {
	// Define your interface here
    public Catalog save(Catalog catalog);
    public Catalog retrieve(String id);
}
