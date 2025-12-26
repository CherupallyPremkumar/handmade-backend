package com.handmade.ecommerce.reconciliation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.reconciliation.service.ReconciliationService;
import com.handmade.ecommerce.reconciliation.service.impl.ReconciliationServiceImpl;
import com.handmade.ecommerce.reconciliation.service.healthcheck.ReconciliationHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class ReconciliationConfiguration {
	@Bean public ReconciliationService _reconciliationService_() {
		return new ReconciliationServiceImpl();
	}

	@Bean ReconciliationHealthChecker reconciliationHealthChecker(){
    	return new ReconciliationHealthChecker();
    }
}
