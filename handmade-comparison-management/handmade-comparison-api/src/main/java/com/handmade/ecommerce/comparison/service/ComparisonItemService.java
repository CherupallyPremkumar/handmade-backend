package com.handmade.ecommerce.comparison.service;

import com.handmade.ecommerce.comparison.model.ComparisonItem;

public interface ComparisonItemService {
	// Define your interface here
    public ComparisonItem save(ComparisonItem comparison);
    public ComparisonItem retrieve(String id);
}
