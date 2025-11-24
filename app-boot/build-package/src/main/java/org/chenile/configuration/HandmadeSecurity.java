package org.chenile.configuration;

import java.util.Arrays;

import org.chenile.configuration.security.ChenileSecurityConfiguration;
import org.chenile.security.KeycloakConnectionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@ConditionalOnProperty(
        name = {"security.config.type"},
        havingValue = "false",
        matchIfMissing = true
)
public class HandmadeSecurity extends ChenileSecurityConfiguration {

    @Autowired
    private KeycloakConnectionDetails connectionDetails;

    @Autowired
    private String realm; // If Chenile registers it as a bean or config property

    @Bean
    @Override
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        String logoutUrl = connectionDetails.host + "/realms/" + realm + "/protocol/openid-connect/logout";

        http
                .csrf().disable()
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:4200"));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("*"));
                    return configuration;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl(logoutUrl)
                )
                .oauth2Login(auth -> {
                    auth.defaultSuccessUrl("/home")
                            .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
                })
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.authenticationManagerResolver(this.authenticationManagerResolver())
                );

        return http.build();
    }

}
