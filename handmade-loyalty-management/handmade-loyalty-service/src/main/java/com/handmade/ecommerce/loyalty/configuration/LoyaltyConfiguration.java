package com.handmade.ecommerce.loyalty.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.loyalty.service.LoyaltyService;
import com.handmade.ecommerce.loyalty.service.impl.LoyaltyServiceImpl;
import com.handmade.ecommerce.loyalty.service.healthcheck.LoyaltyHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class LoyaltyConfiguration {
	@Bean public LoyaltyService _loyaltyService_() {
		return new LoyaltyServiceImpl();
	}

	@Bean LoyaltyHealthChecker loyaltyHealthChecker(){
    	return new LoyaltyHealthChecker();
    }
}
