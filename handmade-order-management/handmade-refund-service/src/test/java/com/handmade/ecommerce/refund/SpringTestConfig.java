package com.handmade.ecommerce.refund;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;



@Configuration
@PropertySource("classpath:com/handmade/ecommerce/refund/TestService.properties")
@SpringBootApplication(scanBasePackages = { "org.chenile.configuration", "com.handmade.ecommerce.refund.configuration" })
@ActiveProfiles("unittest")
@EntityScan(basePackages = { "com.handmade.ecommerce.order.model" })
public class SpringTestConfig extends SpringBootServletInitializer{
	
}

