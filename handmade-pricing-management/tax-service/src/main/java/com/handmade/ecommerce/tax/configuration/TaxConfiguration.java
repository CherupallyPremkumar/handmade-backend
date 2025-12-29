package com.handmade.ecommerce.tax.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.tax.service.TaxService;
import com.handmade.ecommerce.tax.service.impl.TaxServiceImpl;
import com.handmade.ecommerce.tax.service.healthcheck.TaxHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class TaxConfiguration {
	@Bean public TaxService _taxService_() {
		return new TaxServiceImpl();
	}

	@Bean TaxHealthChecker taxHealthChecker(){
    	return new TaxHealthChecker();
    }
}
