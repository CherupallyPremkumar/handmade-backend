package com.handmade.ecommerce.event.configuration;

import com.handmade.ecommerce.event.starter.QueueBasedEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueBasedEventStarterConfiguration {
    
    final Logger logger = LoggerFactory.getLogger(QueueBasedEventStarterConfiguration.class);
    
    @Bean
    QueueBasedEventPublisher queueBasedEventPublisher() {
        logger.info("Creating QueueBasedEventPublisher bean");
        return new QueueBasedEventPublisher();
    }
}
