package com.homebase.ecom;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@PropertySource("classpath:chenile.properties")
@SpringBootApplication(scanBasePackages = { "org.chenile.configuration","com.homebase.ecom.**" })
@ActiveProfiles("unittest")
public class SpringTestConfig extends SpringBootServletInitializer{
}

