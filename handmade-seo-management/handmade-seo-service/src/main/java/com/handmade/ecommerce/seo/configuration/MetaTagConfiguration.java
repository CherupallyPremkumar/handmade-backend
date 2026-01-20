package com.handmade.ecommerce.seo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.seo.service.MetaTagService;
import com.handmade.ecommerce.seo.service.impl.MetaTagServiceImpl;
import com.handmade.ecommerce.seo.service.healthcheck.MetaTagHealthChecker;

/**
 * This is where you will instantiate all the required classes in Spring
 * 
 */
@Configuration
public class MetaTagConfiguration {
	@Bean
	public MetaTagService _metaTagService_() {
		return new MetaTagServiceImpl();
	}

	@Bean
	MetaTagHealthChecker metaTagHealthChecker() {
		return new MetaTagHealthChecker();
	}
}
