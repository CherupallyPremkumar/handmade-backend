package com.handmade.ecommerce.seo.service;

import com.handmade.ecommerce.seo.model.MetaTag;

public interface MetaTagService {
	// Define your interface here
    public MetaTag save(MetaTag seo);
    public MetaTag retrieve(String id);
}
