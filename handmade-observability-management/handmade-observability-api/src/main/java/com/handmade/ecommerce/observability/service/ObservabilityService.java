package com.handmade.ecommerce.observability.service;

public interface ObservabilityService {
	// Define your interface here
    public Observability save(Observability observability);
    public Observability retrieve(String id);
}
