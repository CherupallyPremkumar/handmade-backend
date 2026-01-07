package com.handmade.ecommerce.commission;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@PropertySource("classpath:com/handmade/ecommerce/commission/TestService.properties")
@SpringBootApplication(scanBasePackages = { "org.chenile.configuration",
        "com.handmade.ecommerce.commission.configuration" })
@ActiveProfiles("unittest")
public class SpringTestConfig extends SpringBootServletInitializer {
}
