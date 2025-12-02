package com.handmade.ecommerce.ein.service;

import com.handmade.ecommerce.ein.model.Ein;

public interface EinService {
	// Define your interface here
    public Ein save(Ein ein);
    public Ein retrieve(String id);
}
