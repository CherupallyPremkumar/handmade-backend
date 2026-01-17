package com.handmade.ecommerce.eventqueue;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;


@Configuration
@PropertySource("classpath:com/handmade/ecommerce/eventqueue/TestService.properties")
@SpringBootApplication(scanBasePackages = { "org.chenile.configuration", "com.handmade.ecommerce.eventqueue.configuration" })
@ActiveProfiles("unittest")
@EntityScan(basePackages = { "com.handmade.ecommerce.event.model" })
public class SpringTestConfig extends SpringBootServletInitializer{
	
}

