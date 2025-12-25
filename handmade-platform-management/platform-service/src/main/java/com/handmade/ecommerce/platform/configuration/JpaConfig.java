package com.handmade.ecommerce.platform.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA Configuration for Platform Infrastructure.
 * Configures entity scanning and repository scanning.
 */
@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.handmade.ecommerce.platform.infrastructure.persistence.entity")
@EnableJpaRepositories(basePackages = "com.handmade.ecommerce.platform.infrastructure.persistence.jpa")
public class JpaConfig {
    // Spring Boot auto-configuration will handle most JPA setup
    // This class explicitly defines entity and repository scanning
}
