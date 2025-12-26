package com.handmade.ecommerce.orchestrator;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;


@Configuration
@PropertySource("classpath:com/handmade/ecommerce/orchestrator/TestService.properties")
@SpringBootApplication(scanBasePackages = {  "org.chenile.configuration", "com.handmade.ecommerce.orchestrator.configuration" })
@ActiveProfiles("unittest")
public class SpringTestConfig extends SpringBootServletInitializer{

	
}

