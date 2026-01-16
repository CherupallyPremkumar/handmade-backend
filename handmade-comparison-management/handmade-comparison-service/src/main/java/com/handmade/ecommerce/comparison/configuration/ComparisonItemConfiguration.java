package com.handmade.ecommerce.comparison.configuration;

import com.handmade.ecommerce.comparison.service.ComparisonItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.comparison.service.impl.ComparisonItemServiceImpl;
import com.handmade.ecommerce.comparison.service.healthcheck.ComparisonItemHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration public class ComparisonItemConfiguration {
	@Bean public ComparisonItemService _comparisonItemService_() {
		return new ComparisonItemServiceImpl();
	}

	@Bean ComparisonItemHealthChecker comparisonItemHealthChecker(){
    	return new ComparisonItemHealthChecker();
    }
}
