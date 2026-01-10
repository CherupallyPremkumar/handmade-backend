package com.handmade.ecommerce.core.model;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * RateLimitBucket - Token bucket for API rate limiting
 */
@Entity
@Table(name = "rate_limit_buckets", indexes = {
    @Index(name = "idx_rate_limit_key", columnList = "bucket_key"),
    @Index(name = "idx_rate_limit_window", columnList = "window_start_at")
})
public class RateLimitBucket {
    
    @Id
    @Column(name = "bucket_id", length = 50)
    private String bucketId;
    
    @Column(name = "bucket_key", length = 200, nullable = false, unique = true)
    private String bucketKey; // e.g., "user:123:api" or "ip:1.2.3.4:search"
    
    @Column(name = "token_count", nullable = false)
    private Integer tokenCount;
    
    @Column(name = "max_tokens", nullable = false)
    private Integer maxTokens;
    
    @Column(name = "refill_rate", nullable = false)
    private Double refillRate; // Tokens per second
    
    @Column(name = "last_refill_at", nullable = false)
    private Instant lastRefillAt;
    
    @Column(name = "window_start_at", nullable = false)
    private Instant windowStartAt;
    
    // Constructors
    public RateLimitBucket() {
        this.lastRefillAt = Instant.now();
        this.windowStartAt = Instant.now();
    }
    
    public RateLimitBucket(String bucketKey, Integer maxTokens, Double refillRate) {
        this();
        this.bucketKey = bucketKey;
        this.maxTokens = maxTokens;
        this.tokenCount = maxTokens;
        this.refillRate = refillRate;
    }
    
    // Business methods
    public synchronized boolean allowRequest() {
        refillTokens();
        if (tokenCount > 0) {
            tokenCount--;
            return true;
        }
        return false;
    }
    
    private void refillTokens() {
        Instant now = Instant.now();
        long secondsElapsed = now.getEpochSecond() - lastRefillAt.getEpochSecond();
        
        if (secondsElapsed > 0) {
            int tokensToAdd = (int) (secondsElapsed * refillRate);
            tokenCount = Math.min(tokenCount + tokensToAdd, maxTokens);
            lastRefillAt = now;
        }
    }
    
    // Getters and Setters
    public String getBucketId() { return bucketId; }
    public void setBucketId(String bucketId) { this.bucketId = bucketId; }
    
    public String getBucketKey() { return bucketKey; }
    public void setBucketKey(String bucketKey) { this.bucketKey = bucketKey; }
    
    public Integer getTokenCount() { return tokenCount; }
    public void setTokenCount(Integer tokenCount) { this.tokenCount = tokenCount; }
    
    public Integer getMaxTokens() { return maxTokens; }
    public void setMaxTokens(Integer maxTokens) { this.maxTokens = maxTokens; }
    
    public Double getRefillRate() { return refillRate; }
    public void setRefillRate(Double refillRate) { this.refillRate = refillRate; }
    
    public Instant getLastRefillAt() { return lastRefillAt; }
    public void setLastRefillAt(Instant lastRefillAt) { this.lastRefillAt = lastRefillAt; }
    
    public Instant getWindowStartAt() { return windowStartAt; }
    public void setWindowStartAt(Instant windowStartAt) { this.windowStartAt = windowStartAt; }
}
