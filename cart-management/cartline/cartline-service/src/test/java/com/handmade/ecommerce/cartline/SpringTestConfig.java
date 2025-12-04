package com.handmade.ecommerce.cartline;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.cartline.model.Cartline;


@Configuration
@PropertySource("classpath:com/handmade/ecommerce/cartline/TestService.properties")
@SpringBootApplication(scanBasePackages = { "org.chenile.configuration", "com.handmade.ecommerce.cartline.configuration" })
@ActiveProfiles("unittest")
public class SpringTestConfig extends SpringBootServletInitializer{
	
}

