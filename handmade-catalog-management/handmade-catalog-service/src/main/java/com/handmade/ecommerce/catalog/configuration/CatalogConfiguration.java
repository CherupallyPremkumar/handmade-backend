package com.handmade.ecommerce.catalog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.catalog.service.CatalogService;
import com.handmade.ecommerce.catalog.service.impl.CatalogServiceImpl;
import com.handmade.ecommerce.catalog.service.healthcheck.CatalogHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class CatalogConfiguration {
	@Bean public CatalogService _catalogService_() {
		return new CatalogServiceImpl();
	}

	@Bean CatalogHealthChecker catalogHealthChecker(){
    	return new CatalogHealthChecker();
    }
}
