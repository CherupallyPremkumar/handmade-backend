package com.handmade.ecommerce.observability.service;

import com.handmade.ecommerce.observability.model.Observability;

public interface ObservabilityService {
	// Define your interface here
    public Observability save(Observability observability);
    public Observability retrieve(String id);
}
