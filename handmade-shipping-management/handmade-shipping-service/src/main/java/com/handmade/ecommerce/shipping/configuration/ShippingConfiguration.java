package com.handmade.ecommerce.shipping.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.shipping.service.ShippingService;
import com.handmade.ecommerce.shipping.service.impl.ShippingServiceImpl;
import com.handmade.ecommerce.shipping.service.healthcheck.ShippingHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class ShippingConfiguration {
	@Bean public ShippingService _shippingService_() {
		return new ShippingServiceImpl();
	}

	@Bean ShippingHealthChecker shippingHealthChecker(){
    	return new ShippingHealthChecker();
    }
}
