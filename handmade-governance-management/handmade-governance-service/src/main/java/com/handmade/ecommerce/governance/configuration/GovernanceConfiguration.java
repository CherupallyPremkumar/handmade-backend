package com.handmade.ecommerce.governance.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.governance.service.GovernanceService;
import com.handmade.ecommerce.governance.service.impl.GovernanceServiceImpl;
import com.handmade.ecommerce.governance.service.healthcheck.GovernanceHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class GovernanceConfiguration {
	@Bean public GovernanceService _governanceService_() {
		return new GovernanceServiceImpl();
	}

	@Bean GovernanceHealthChecker governanceHealthChecker(){
    	return new GovernanceHealthChecker();
    }
}
