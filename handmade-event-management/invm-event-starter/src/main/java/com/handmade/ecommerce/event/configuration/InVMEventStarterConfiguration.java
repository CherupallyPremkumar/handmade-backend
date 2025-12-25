package com.handmade.ecommerce.event.configuration;

import com.handmade.ecommerce.event.starter.InVMEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InVMEventStarterConfiguration {
    
    final Logger logger = LoggerFactory.getLogger(InVMEventStarterConfiguration.class);
    
    @Bean
    InVMEventPublisher inVMEventPublisher() {
        logger.info("Creating InVMEventPublisher bean");
        return new InVMEventPublisher();
    }
}
