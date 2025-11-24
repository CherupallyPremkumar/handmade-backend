package com.handmade.ecommerce.common;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;


@Configuration
@PropertySource("classpath:com/handmade/ecommerce/common/TestService.properties")
@SpringBootApplication(scanBasePackages = {  "org.chenile.configuration", "com.handmade.ecommerce.common.configuration" })
@ActiveProfiles("unittest")
public class SpringTestConfig extends SpringBootServletInitializer{

	
}

