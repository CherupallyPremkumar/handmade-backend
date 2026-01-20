package com.handmade.ecommerce.contentpage;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;




@Configuration
@PropertySource("classpath:com/handmade/ecommerce/contentpage/TestService.properties")
@SpringBootApplication(scanBasePackages = { "org.chenile.configuration", "com.handmade.ecommerce.contentpage.configuration" ,"com.handmade.ecommerce.cms" })
@ActiveProfiles("unittest")
@EntityScan(basePackages = { "com.handmade.ecommerce.cms.model" })
public class SpringTestConfig extends SpringBootServletInitializer{
	
}

