package com.handmade.ecommerce.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.common.service.CommonService;
import com.handmade.ecommerce.common.service.impl.CommonServiceImpl;
import com.handmade.ecommerce.common.service.healthcheck.CommonHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class CommonConfiguration {
	@Bean public CommonService _commonService_() {
		return new CommonServiceImpl();
	}

	@Bean CommonHealthChecker commonHealthChecker(){
    	return new CommonHealthChecker();
    }
}
