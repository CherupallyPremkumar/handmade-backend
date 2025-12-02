package com.handmade.ecommerce.razorpay.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.handmade.ecommerce.razorpay.service.RazorpayService;
import com.handmade.ecommerce.razorpay.service.impl.RazorpayServiceImpl;
import com.handmade.ecommerce.razorpay.service.healthcheck.RazorpayHealthChecker;

/**
 This is where you will instantiate all the required classes in Spring

*/
@Configuration
public class RazorpayConfiguration {
	@Bean public RazorpayService _razorpayService_() {
		return new RazorpayServiceImpl();
	}

	@Bean RazorpayHealthChecker razorpayHealthChecker(){
    	return new RazorpayHealthChecker();
    }
}
