package com.handmade.ecommerce.platform.configuration;

import com.handmade.ecommerce.platform.starter.QueueBasedPlatformEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueBasedPlatformStarterConfiguration {
    
    final Logger logger = LoggerFactory.getLogger(QueueBasedPlatformStarterConfiguration.class);
    
    @Bean
    QueueBasedPlatformEventPublisher queueBasedPlatformEventPublisher() {
        logger.info("Creating QueueBasedPlatformEventPublisher bean");
        return new QueueBasedPlatformEventPublisher();
    }
}
