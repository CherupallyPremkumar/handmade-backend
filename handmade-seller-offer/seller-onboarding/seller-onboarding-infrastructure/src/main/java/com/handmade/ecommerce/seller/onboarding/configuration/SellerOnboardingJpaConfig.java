package com.handmade.ecommerce.seller.onboarding.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.handmade.ecommerce.seller.onboarding.persistence.repository")
@EntityScan(basePackages = "com.handmade.ecommerce.seller.onboarding.persistence.entity")
public class SellerOnboardingJpaConfig {
}
