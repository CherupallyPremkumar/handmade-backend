package com.homebase.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HomebaseAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebaseAdminApplication.class, args);
    }
}
