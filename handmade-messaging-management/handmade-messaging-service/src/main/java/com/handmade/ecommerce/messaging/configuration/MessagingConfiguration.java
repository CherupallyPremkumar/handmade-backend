package com.handmade.ecommerce.messaging.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.messaging.service.MessagingService;
import com.handmade.ecommerce.messaging.service.impl.MessagingServiceImpl;
import com.handmade.ecommerce.messaging.service.healthcheck.MessagingHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class MessagingConfiguration {
	@Bean public MessagingService _messagingService_() {
		return new MessagingServiceImpl();
	}

	@Bean MessagingHealthChecker messagingHealthChecker(){
    	return new MessagingHealthChecker();
    }
}
