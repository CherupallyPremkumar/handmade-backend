package com.handmade.ecommerce.observability.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.observability.service.ObservabilityService;
import com.handmade.ecommerce.observability.service.impl.ObservabilityServiceImpl;
import com.handmade.ecommerce.observability.service.healthcheck.ObservabilityHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class ObservabilityConfiguration {
	@Bean public ObservabilityService _observabilityService_() {
		return new ObservabilityServiceImpl();
	}

	@Bean ObservabilityHealthChecker observabilityHealthChecker(){
    	return new ObservabilityHealthChecker();
    }
}
