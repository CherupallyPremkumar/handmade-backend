package com.homebase.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"org.chenile.configuration.**", "com.homebase.ecom.**"})
public class HomebaseAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebaseAdminApplication.class, args);
    }
}
