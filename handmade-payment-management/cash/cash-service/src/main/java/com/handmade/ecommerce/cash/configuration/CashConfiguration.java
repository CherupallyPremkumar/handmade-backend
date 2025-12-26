package com.handmade.ecommerce.cash.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.cash.service.CashService;
import com.handmade.ecommerce.cash.service.impl.CashServiceImpl;
import com.handmade.ecommerce.cash.service.healthcheck.CashHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class CashConfiguration {
	@Bean public CashService _cashService_() {
		return new CashServiceImpl();
	}

	@Bean CashHealthChecker cashHealthChecker(){
    	return new CashHealthChecker();
    }
}
