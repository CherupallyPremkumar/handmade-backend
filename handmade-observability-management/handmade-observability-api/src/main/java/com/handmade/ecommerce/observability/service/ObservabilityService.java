package com.handmade.ecommerce.observability.service;

import com.handmade.ecommerce.observability.model.MetricsSnapshot;

public interface ObservabilityService {
	// Define your interface here
    public MetricsSnapshot save(MetricsSnapshot observability);
    public MetricsSnapshot retrieve(String id);
}
