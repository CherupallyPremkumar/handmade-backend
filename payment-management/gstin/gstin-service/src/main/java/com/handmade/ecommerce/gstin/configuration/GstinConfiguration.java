package com.handmade.ecommerce.gstin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.gstin.service.GstinService;
import com.handmade.ecommerce.gstin.service.impl.GstinServiceImpl;
import com.handmade.ecommerce.gstin.service.healthcheck.GstinHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class GstinConfiguration {
	@Bean public GstinService _gstinService_() {
		return new GstinServiceImpl();
	}

	@Bean GstinHealthChecker gstinHealthChecker(){
    	return new GstinHealthChecker();
    }
}
