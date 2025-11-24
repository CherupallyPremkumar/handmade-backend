package com.handmade.ecommerce.common.service;

import com.handmade.ecommerce.common.model.Common;

public interface CommonService {
	// Define your interface here
    public Common save(Common common);
    public Common retrieve(String id);
}
