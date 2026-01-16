package com.handmade.ecommerce.shipping.service;

import com.handmade.ecommerce.shipping.model.Shipping;

public interface ShippingService {
	// Define your interface here
    public Shipping save(Shipping shipping);
    public Shipping retrieve(String id);
}
