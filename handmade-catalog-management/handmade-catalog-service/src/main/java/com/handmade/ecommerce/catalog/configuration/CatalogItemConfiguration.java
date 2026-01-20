package com.handmade.ecommerce.catalog.configuration;

import com.handmade.ecommerce.catalog.service.CatalogItemService;
import com.handmade.ecommerce.catalog.service.healthcheck.CatalogItemHealthChecker;
import com.handmade.ecommerce.catalog.service.impl.CatalogItemServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class CatalogItemConfiguration {
	@Bean public CatalogItemService _catalogItemService_() {
		return new CatalogItemServiceImpl();
	}

	@Bean
	CatalogItemHealthChecker catalogItemHealthChecker(){
    	return new CatalogItemHealthChecker();
    }
}
