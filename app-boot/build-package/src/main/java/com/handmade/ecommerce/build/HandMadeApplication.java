package com.handmade.ecommerce.build;

import org.chenile.core.context.ChenileExchange;
import org.chenile.security.SecurityServiceImpl;
import org.chenile.security.service.SecurityService;
import org.chenile.security.service.impl.StmSecurityStrategyImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {
        "com.handmade.ecommerce.**", "com.handmade.storefront.**", "org.chenile.configuration.**",
})

@EntityScan("com.handmade.ecommerce.**")
@EnableJpaRepositories("com.handmade.ecommerce.**")
public class HandMadeApplication {
    public static void main(String[] args) {
        SpringApplication.run(HandMadeApplication.class, args);
    }
}
