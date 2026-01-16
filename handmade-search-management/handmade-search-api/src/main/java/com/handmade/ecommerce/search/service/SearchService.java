package com.handmade.ecommerce.search.service;

import com.handmade.ecommerce.search.model.Search;

public interface SearchService {
	// Define your interface here
    public Search save(Search search);
    public Search retrieve(String id);
}
