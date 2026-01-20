package com.handmade.ecommerce.customer.configuration;

import com.handmade.ecommerce.customer.service.impl.CustomerProfileServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.customer.service.healthcheck.CustomerHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class CustomerConfiguration {
	@Bean public CustomerProfileServiceImpl _customerService_() {
		return new CustomerProfileServiceImpl();
	}

	@Bean CustomerHealthChecker customerHealthChecker(){
    	return new CustomerHealthChecker();
    }
}
