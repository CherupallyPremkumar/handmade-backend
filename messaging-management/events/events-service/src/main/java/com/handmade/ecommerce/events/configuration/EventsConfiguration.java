package com.handmade.ecommerce.events.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.events.service.EventsService;
import com.handmade.ecommerce.events.service.impl.EventsServiceImpl;
import com.handmade.ecommerce.events.service.healthcheck.EventsHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class EventsConfiguration {
	@Bean public EventsService _eventsService_() {
		return new EventsServiceImpl();
	}

	@Bean EventsHealthChecker eventsHealthChecker(){
    	return new EventsHealthChecker();
    }
}
