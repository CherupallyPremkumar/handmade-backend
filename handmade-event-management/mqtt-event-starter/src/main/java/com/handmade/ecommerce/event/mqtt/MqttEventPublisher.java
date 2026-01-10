package com.handmade.ecommerce.event.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.event.api.EventPublisher;
import org.chenile.mqtt.pubsub.MqttPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;

public class MqttEventPublisher implements EventPublisher {

    private final Logger logger = LoggerFactory.getLogger(MqttEventPublisher.class);
    
    @Autowired
    private MqttPublisher mqttPublisher;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void publish(String topic, Object event) {
        logger.info("Publishing event to MQTT topic: {}", topic);
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            Map<String, Object> properties = new HashMap<>();
            // MqttPublisher.publish(topic, payload, properties)
            mqttPublisher.publish(topic, eventJson, properties);
        } catch (Exception e) {
            logger.error("Failed to publish event to MQTT topic: " + topic, e);
        }
    }
}
