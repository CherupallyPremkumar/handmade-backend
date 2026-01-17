package com.handmade.ecommerce.auditprocess;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;



@Configuration
@PropertySource("classpath:com/handmade/ecommerce/auditprocess/TestService.properties")
@SpringBootApplication(scanBasePackages = { "org.chenile.configuration", "com.handmade.ecommerce.auditprocess.configuration" })
@ActiveProfiles("unittest")
@EntityScan(basePackages = { "com.handmade.ecommerce.governance.model" })
public class SpringTestConfig extends SpringBootServletInitializer{
	
}

