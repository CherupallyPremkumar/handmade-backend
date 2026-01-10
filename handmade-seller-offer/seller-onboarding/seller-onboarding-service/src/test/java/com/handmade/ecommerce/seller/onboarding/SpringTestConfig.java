package com.handmade.ecommerce.seller.onboarding;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.context.annotation.Bean;

@Configuration
@PropertySource("classpath:com/handmade/ecommerce/seller/onboarding/TestService.properties")
@SpringBootApplication(scanBasePackages = { "org.chenile.configuration", "com.handmade.ecommerce.seller.onboarding",
        "com.handmade.ecommerce.event" })
@ActiveProfiles("unittest")
@EnableJpaRepositories(basePackages = "com.handmade.ecommerce.seller.onboarding.repository")
@EntityScan(basePackages = "com.handmade.ecommerce.seller.onboarding.entity")
public class SpringTestConfig {

    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }
}
