package com.handmade.ecommerce.cart;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.cart.model.Cart;


@Configuration
@PropertySource("classpath:com/handmade/ecommerce/cart/TestService.properties")
@SpringBootApplication(scanBasePackages = { "org.chenile.configuration", "com.handmade.ecommerce.cart.configuration" })
@ActiveProfiles("unittest")
public class SpringTestConfig extends SpringBootServletInitializer{
	
}

