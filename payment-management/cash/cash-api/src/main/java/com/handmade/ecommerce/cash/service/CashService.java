package com.handmade.ecommerce.cash.service;

import com.handmade.ecommerce.cash.model.Cash;

public interface CashService {
	// Define your interface here
    public Cash save(Cash cash);
    public Cash retrieve(String id);
}
