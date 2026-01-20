package com.handmade.ecommerce.tax.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.tax.service.impl.TaxRateServiceImpl;
import com.handmade.ecommerce.tax.service.healthcheck.TaxRateHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class TaxRateConfiguration {
	@Bean public TaxRateServiceImpl _taxRateService_() {
		return new TaxRateServiceImpl();
	}

	@Bean TaxRateHealthChecker taxRateHealthChecker(){
    	return new TaxRateHealthChecker();
    }
}
