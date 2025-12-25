package com.handmade.ecommerce.platform.starter;

import org.chenile.pubsub.configuration.PubSubConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    com.handmade.ecommerce.platform.service.impl.PlatformManagerImpl.class,
    PubSubConfiguration.class
})
public class QueueBasedPlatformConfiguration {
}
