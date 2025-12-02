package com.handmade.ecommerce.orchestrator.service;

import com.handmade.ecommerce.orchestrator.model.Orchestrator;

public interface OrchestratorService {
	// Define your interface here
    public Orchestrator save(Orchestrator orchestrator);
    public Orchestrator retrieve(String id);
}
