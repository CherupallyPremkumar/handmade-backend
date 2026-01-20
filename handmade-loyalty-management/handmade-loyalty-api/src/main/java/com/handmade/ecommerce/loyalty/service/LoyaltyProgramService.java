package com.handmade.ecommerce.loyalty.service;

import com.handmade.ecommerce.loyalty.model.LoyaltyProgram;

public interface LoyaltyProgramService {
	// Define your interface here
    public LoyaltyProgram save(LoyaltyProgram loyalty);
    public LoyaltyProgram retrieve(String id);
}
