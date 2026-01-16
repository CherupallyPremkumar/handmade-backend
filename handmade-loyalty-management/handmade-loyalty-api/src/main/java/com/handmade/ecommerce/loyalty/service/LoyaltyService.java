package com.handmade.ecommerce.loyalty.service;

import com.handmade.ecommerce.loyalty.model.Loyalty;

public interface LoyaltyService {
	// Define your interface here
    public Loyalty save(Loyalty loyalty);
    public Loyalty retrieve(String id);
}
