package com.handmade.ecommerce.platform.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@SpringBootApplication(scanBasePackages = { 
    "org.chenile.configuration", 
    "com.handmade.ecommerce.platform.configuration",
    "com.handmade.ecommerce.event.configuration"
})
@PropertySource("classpath:application-unittest.properties")
@EntityScan({"com.handmade.ecommerce.platform.domain"})
@EnableJpaRepositories(basePackages = {"com.handmade.ecommerce.platform.configuration.dao"})
@ActiveProfiles("unittest")
public class SpringConfig extends SpringBootServletInitializer{
    
    @org.springframework.context.annotation.Bean
    public com.handmade.ecommerce.event.api.EventPublisher eventPublisher() {
        return (topic, event) -> {
            System.out.println("Test Event Published: " + topic + " " + event);
        };
    }
}
