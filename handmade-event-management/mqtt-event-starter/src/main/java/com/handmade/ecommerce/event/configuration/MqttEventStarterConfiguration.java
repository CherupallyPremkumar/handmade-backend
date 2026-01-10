package com.handmade.ecommerce.event.configuration;

import com.handmade.ecommerce.event.mqtt.MqttEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttEventStarterConfiguration {
    
    final Logger logger = LoggerFactory.getLogger(MqttEventStarterConfiguration.class);
    
    @Bean
    MqttEventPublisher mqttEventPublisher() {
        logger.info("Creating MqttEventPublisher bean");
        return new MqttEventPublisher();
    }
}
