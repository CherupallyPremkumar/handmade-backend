package com.handmade.ecommerce.search.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.search.service.SearchService;
import com.handmade.ecommerce.search.service.impl.SearchServiceImpl;
import com.handmade.ecommerce.search.service.healthcheck.SearchHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class SearchConfiguration {
	@Bean public SearchService _searchService_() {
		return new SearchServiceImpl();
	}

	@Bean SearchHealthChecker searchHealthChecker(){
    	return new SearchHealthChecker();
    }
}
