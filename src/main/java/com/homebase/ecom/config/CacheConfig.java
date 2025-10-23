package com.homebase.ecom.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Cache configuration for the application.
 * Uses in-memory caching by default, can be extended to use Redis in production.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Simple in-memory cache manager for development and testing
     */
    @Bean
    @Profile({"dev", "local", "h2"})
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(
            "priceLines",
            "prices",
            "products",
            "customers"
        );
    }
    
    /**
     * TODO: Add Redis cache manager for production
     * 
     * @Bean
     * @Profile("production")
     * public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
     *     RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
     *         .entryTtl(Duration.ofHours(1))
     *         .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
     *         .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
     *     
     *     return RedisCacheManager.builder(connectionFactory)
     *         .cacheDefaults(config)
     *         .build();
     * }
     */
}
