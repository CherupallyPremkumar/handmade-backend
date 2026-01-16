package com.handmade.ecommerce.seo.service;

import com.handmade.ecommerce.seo.model.Seo;

public interface SeoService {
	// Define your interface here
    public Seo save(Seo seo);
    public Seo retrieve(String id);
}
