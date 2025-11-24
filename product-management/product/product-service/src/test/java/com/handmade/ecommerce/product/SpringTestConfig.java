package com.handmade.ecommerce.product;


import jakarta.persistence.Entity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.product.model.Product;


@Configuration
@PropertySource("classpath:com/handmade/ecommerce/product/TestService.properties")
@SpringBootApplication(scanBasePackages = { "org.chenile.configuration", "com.handmade.ecommerce.product.configuration",
"com.handmade.ecommerce.event.configuration"})
@EntityScan(basePackages = { "com.handmade.ecommerce.common.model", "com.handmade.ecommerce.product.model"})
@EnableJpaRepositories(basePackages = { "com.handmade.ecommerce.product.configuration.dao"})
@ActiveProfiles("unittest")
public class SpringTestConfig extends SpringBootServletInitializer{
	
}

