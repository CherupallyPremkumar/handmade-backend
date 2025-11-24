package com.handmade.ecommerce.location.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.location.service.LocationService;
import com.handmade.ecommerce.location.service.impl.LocationServiceImpl;
import com.handmade.ecommerce.location.service.healthcheck.LocationHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class LocationConfiguration {
	@Bean public LocationService _locationService_() {
		return new LocationServiceImpl();
	}

	@Bean LocationHealthChecker locationHealthChecker(){
    	return new LocationHealthChecker();
    }
}
