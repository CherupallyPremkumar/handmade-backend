package com.handmade.ecommerce.stripe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.stripe.service.StripeService;
import com.handmade.ecommerce.stripe.service.impl.StripeServiceImpl;
import com.handmade.ecommerce.stripe.service.healthcheck.StripeHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class StripeConfiguration {
	@Bean public StripeService _stripeService_() {
		return new StripeServiceImpl();
	}

	@Bean StripeHealthChecker stripeHealthChecker(){
    	return new StripeHealthChecker();
    }
}
