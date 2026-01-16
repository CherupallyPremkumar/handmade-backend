package com.handmade.ecommerce.customer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.customer.service.CustomerService;
import com.handmade.ecommerce.customer.service.impl.CustomerServiceImpl;
import com.handmade.ecommerce.customer.service.healthcheck.CustomerHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class CustomerConfiguration {
	@Bean public CustomerService _customerService_() {
		return new CustomerServiceImpl();
	}

	@Bean CustomerHealthChecker customerHealthChecker(){
    	return new CustomerHealthChecker();
    }
}
