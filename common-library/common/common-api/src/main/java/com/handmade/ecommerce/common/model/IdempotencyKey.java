package com.handmade.ecommerce.common.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * IdempotencyKey - Ensures idempotent API operations
 * Prevents duplicate processing of requests (e.g., duplicate orders from retries)
 */
@Entity
@Table(name = "idempotency_keys", indexes = {
    @Index(name = "idx_idempotency_expires_at", columnList = "expires_at")
})
public class IdempotencyKey {
    
    @Id
    @Column(name = "idempotency_key", length = 100)
    private String idempotencyKey;
    
    @Column(name = "resource_type", length = 50, nullable = false)
    private String resourceType; // "Order", "Payment", etc.
    
    @Column(name = "resource_id", length = 50)
    private String resourceId; // Created resource ID
    
    @Column(name = "request_hash", length = 64)
    private String requestHash; // SHA-256 hash of request body
    
    @Column(name = "response_data", columnDefinition = "TEXT")
    private String responseData; // Cached response (JSON)
    
    @Column(name = "status_code")
    private Integer statusCode; // HTTP status code
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;
    
    // Constructors
    public IdempotencyKey() {
        this.createdAt = Instant.now();
        this.expiresAt = Instant.now().plusSeconds(24 * 60 * 60); // 24 hours
    }
    
    public IdempotencyKey(String idempotencyKey, String resourceType) {
        this();
        this.idempotencyKey = idempotencyKey;
        this.resourceType = resourceType;
    }
    
    // Business methods
    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
    
    public void setResponse(String resourceId, String responseData, Integer statusCode) {
        this.resourceId = resourceId;
        this.responseData = responseData;
        this.statusCode = statusCode;
    }
    
    // Getters and Setters
    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
    
    public String getResourceType() { return resourceType; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }
    
    public String getResourceId() { return resourceId; }
    public void setResourceId(String resourceId) { this.resourceId = resourceId; }
    
    public String getRequestHash() { return requestHash; }
    public void setRequestHash(String requestHash) { this.requestHash = requestHash; }
    
    public String getResponseData() { return responseData; }
    public void setResponseData(String responseData) { this.responseData = responseData; }
    
    public Integer getStatusCode() { return statusCode; }
    public void setStatusCode(Integer statusCode) { this.statusCode = statusCode; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
