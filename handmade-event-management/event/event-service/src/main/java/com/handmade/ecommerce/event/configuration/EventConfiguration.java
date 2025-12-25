package com.handmade.ecommerce.event.configuration;

import com.handmade.ecommerce.event.EventBus;
import com.handmade.ecommerce.event.service.CoreEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.event.service.EventService;
import com.handmade.ecommerce.event.service.impl.EventServiceImpl;
import com.handmade.ecommerce.event.service.healthcheck.EventHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class EventConfiguration {
	@Bean public EventService _eventService_() {
		return new EventServiceImpl();
	}

	@Bean
	EventBus eventBus(){
		return new CoreEventBus();
	}

	@Bean EventHealthChecker eventHealthChecker(){
    	return new EventHealthChecker();
    }
}
