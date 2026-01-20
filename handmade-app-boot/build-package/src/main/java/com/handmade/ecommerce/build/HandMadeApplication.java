package com.handmade.ecommerce.build;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {
        "com.handmade.ecommerce.db.changelog.**", "com.handmade.ecommerce.**", "org.chenile.configuration.**",
        "com.handmade.ecommerce.query.service.mapper"
})
@EntityScan("com.handmade.ecommerce.**.model")
@EnableJpaRepositories("com.handmade.ecommerce.**.configuration.dao")
public class HandMadeApplication {
    public static void main(String[] args) {
        SpringApplication.run(HandMadeApplication.class, args);
    }

    @Bean
    public org.springframework.web.filter.CorsFilter corsFilter() {
        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new org.springframework.web.filter.CorsFilter(source);
    }
}
