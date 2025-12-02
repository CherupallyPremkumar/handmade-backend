package com.handmade.ecommerce.ein.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.ein.service.EinService;
import com.handmade.ecommerce.ein.service.impl.EinServiceImpl;
import com.handmade.ecommerce.ein.service.healthcheck.EinHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class EinConfiguration {
	@Bean public EinService _einService_() {
		return new EinServiceImpl();
	}

	@Bean EinHealthChecker einHealthChecker(){
    	return new EinHealthChecker();
    }
}
