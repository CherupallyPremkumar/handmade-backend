package com.handmade.ecommerce.localization.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.localization.service.LocalizationService;
import com.handmade.ecommerce.localization.service.impl.LocalizationServiceImpl;
import com.handmade.ecommerce.localization.service.healthcheck.LocalizationHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class LocalizationConfiguration {
	@Bean public LocalizationService _localizationService_() {
		return new LocalizationServiceImpl();
	}

	@Bean LocalizationHealthChecker localizationHealthChecker(){
    	return new LocalizationHealthChecker();
    }
}
