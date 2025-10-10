package com.homebase.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Security Headers Configuration
 * Adds security headers to prevent common web vulnerabilities
 */
@Configuration
public class SecurityHeadersConfig {

    @Bean
    public SecurityHeadersFilter securityHeadersFilter() {
        return new SecurityHeadersFilter();
    }

    public static class SecurityHeadersFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, 
                                       HttpServletResponse response, 
                                       FilterChain filterChain) 
                throws ServletException, IOException {
            
            // Prevent clickjacking
            response.setHeader("X-Frame-Options", "DENY");
            
            // Prevent MIME type sniffing
            response.setHeader("X-Content-Type-Options", "nosniff");
            
            // Enable XSS protection
            response.setHeader("X-XSS-Protection", "1; mode=block");
            
            // Content Security Policy - prevents inline scripts and XSS
            response.setHeader("Content-Security-Policy", 
                "default-src 'self'; " +
                "script-src 'self' 'unsafe-inline' 'unsafe-eval'; " +
                "style-src 'self' 'unsafe-inline'; " +
                "img-src 'self' data: https:; " +
                "font-src 'self' data:; " +
                "connect-src 'self'");
            
            // Strict Transport Security - enforce HTTPS
            response.setHeader("Strict-Transport-Security", 
                "max-age=31536000; includeSubDomains");
            
            // Referrer Policy
            response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
            
            // Permissions Policy (formerly Feature Policy)
            response.setHeader("Permissions-Policy", 
                "geolocation=(), microphone=(), camera=()");
            
            // Prevent caching of sensitive data
            if (request.getRequestURI().contains("/api/admin") || 
                request.getRequestURI().contains("/api/user")) {
                response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, private");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Expires", "0");
            }
            
            filterChain.doFilter(request, response);
        }
    }
}
