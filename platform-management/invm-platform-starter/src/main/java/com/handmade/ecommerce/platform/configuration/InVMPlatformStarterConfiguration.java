package com.handmade.ecommerce.platform.configuration;

import com.handmade.ecommerce.platform.service.defs.PostSaveHook;
import com.handmade.ecommerce.platform.starter.InVMPlatformEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InVMPlatformStarterConfiguration {
    
    final Logger logger = LoggerFactory.getLogger(InVMPlatformStarterConfiguration.class);
    
    @Bean
    InVMPlatformEventPublisher inVMPlatformEventPublisher() {
        logger.info("Creating InVMPlatformEventPublisher bean");
        return new InVMPlatformEventPublisher();
    }
}
