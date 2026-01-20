package com.handmade.ecommerce.scheduler.configuration;

import com.handmade.ecommerce.event.service.SchedulerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.scheduler.service.impl.SchedulerServiceImpl;
import com.handmade.ecommerce.scheduler.service.healthcheck.SchedulerHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class SchedulerConfiguration {
	@Bean public SchedulerService _schedulerService_() {
		return new SchedulerServiceImpl();
	}

	@Bean SchedulerHealthChecker schedulerHealthChecker(){
    	return new SchedulerHealthChecker();
    }
}
