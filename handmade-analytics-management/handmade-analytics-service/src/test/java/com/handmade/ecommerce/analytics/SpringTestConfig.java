package com.handmade.ecommerce.analytics;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import org.chenile.utils.entity.service.EntityStore;

@Configuration
@PropertySource("classpath:com/handmade/ecommerce/analytics/TestService.properties")
@SpringBootApplication(
        scanBasePackages = {
                "org.chenile.configuration",
                "com.handmade.ecommerce.analytics.configuration"
        }
)
public class SpringTestConfig extends SpringBootServletInitializer{
	
}

