package com.handmade.ecommerce.integration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.integration.service.IntegrationService;
import com.handmade.ecommerce.integration.service.impl.IntegrationServiceImpl;
import com.handmade.ecommerce.integration.service.healthcheck.IntegrationHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class IntegrationConfiguration {
	@Bean public IntegrationService _integrationService_() {
		return new IntegrationServiceImpl();
	}

	@Bean IntegrationHealthChecker integrationHealthChecker(){
    	return new IntegrationHealthChecker();
    }
}
