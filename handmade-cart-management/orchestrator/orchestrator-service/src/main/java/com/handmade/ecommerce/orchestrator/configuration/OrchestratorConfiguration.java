package com.handmade.ecommerce.orchestrator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.orchestrator.service.OrchestratorService;
import com.handmade.ecommerce.orchestrator.service.impl.OrchestratorServiceImpl;
import com.handmade.ecommerce.orchestrator.service.healthcheck.OrchestratorHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class OrchestratorConfiguration {
	@Bean public OrchestratorService _orchestratorService_() {
		return new OrchestratorServiceImpl();
	}

	@Bean OrchestratorHealthChecker orchestratorHealthChecker(){
    	return new OrchestratorHealthChecker();
    }
}
