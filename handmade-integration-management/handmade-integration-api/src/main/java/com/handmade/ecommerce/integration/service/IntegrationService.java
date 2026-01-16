package com.handmade.ecommerce.integration.service;

import com.handmade.ecommerce.integration.model.Integration;

public interface IntegrationService {
	// Define your interface here
    public Integration save(Integration integration);
    public Integration retrieve(String id);
}
