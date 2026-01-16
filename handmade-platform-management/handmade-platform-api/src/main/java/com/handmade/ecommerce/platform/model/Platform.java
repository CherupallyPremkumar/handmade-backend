package com.handmade.ecommerce.platform.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * Platform Entity - Represents a marketplace platform (e.g., US, IN, UK)
 * Managed by Chenile STM for lifecycle management
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_platform")
public class Platform extends AbstractJpaStateEntity {

    @Column(name = "platform_code", length = 50, nullable = false, unique = true)
    private String platformCode;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "country_code", length = 2, nullable = false)
    private String countryCode;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode;

    @Column(name = "marketplace_type", length = 20, nullable = false)
    private String marketplaceType; // B2C, B2B

    @Column(name = "description", length = 500)
    private String description;


    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Relationships
    @OneToMany(mappedBy = "platform", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<PlatformFeatureConfig> featureConfigs;

    @OneToMany(mappedBy = "platform", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<PlatformPolicy> policies;
}
