package com.handmade.ecommerce.platform.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Lob;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Stores feature flags and configuration for the platform.
 * Persisted as a JSON text blob for flexibility (Key-Value Extensions).
 * PURE DOMAIN MODEL (no persistence annotations).
 */
@Entity
@Table(name = "hm_platform_feature_config")
public class PlatformFeatureConfig extends BaseJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String platformId;
    @Lob
    private String configJson;
    private Long version; // Optimistic lock
    private LocalDateTime updatedAt;

    protected PlatformFeatureConfig() {
    }

    public PlatformFeatureConfig(String platformId, String configJson) {
        this.platformId = platformId;
        this.configJson = configJson;
        this.updatedAt = LocalDateTime.now();
        this.version = 1L;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getConfigJson() {
        return configJson;
    }

    public void setConfigJson(String configJson) {
        this.configJson = configJson;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
