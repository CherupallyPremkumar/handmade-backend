package com.handmade.ecommerce.loyalty.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.loyalty.service.LoyaltyProgramService;
import com.handmade.ecommerce.loyalty.service.impl.LoyaltyProgramServiceImpl;
import com.handmade.ecommerce.loyalty.service.healthcheck.LoyaltyProgramHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class LoyaltyProgramConfiguration {
	@Bean public LoyaltyProgramService _loyaltyService_() {
		return new LoyaltyProgramServiceImpl();
	}

	@Bean LoyaltyProgramHealthChecker loyaltyHealthChecker(){
    	return new LoyaltyProgramHealthChecker();
    }
}
