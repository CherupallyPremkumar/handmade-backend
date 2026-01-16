package com.handmade.ecommerce.seo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.seo.service.SeoService;
import com.handmade.ecommerce.seo.service.impl.SeoServiceImpl;
import com.handmade.ecommerce.seo.service.healthcheck.SeoHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class SeoConfiguration {
	@Bean public SeoService _seoService_() {
		return new SeoServiceImpl();
	}

	@Bean SeoHealthChecker seoHealthChecker(){
    	return new SeoHealthChecker();
    }
}
