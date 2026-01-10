package com.handmade.ecommerce.seller.account.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = {"com.handmade.ecommerce.seller", "org.chenile.configuration"})
@PropertySource("classpath:application-unittest.properties")
public class SpringConfig {

}
