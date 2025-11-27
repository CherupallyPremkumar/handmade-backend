package com.handmade.ecommerce.pricing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.AbstractJpaEntity;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Region Entity
 * Represents geographical regions for pricing and tax purposes
 */
@Entity
@Table(name = "hm_region")
@Getter
@Setter
public class Region extends AbstractJpaStateEntity {
    
    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code; // US, EU, IN, etc.
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "default_currency", nullable = false, length = 3)
    private String defaultCurrency;
    
    /**
     * JSON array of supported currency codes
     * Example: ["USD", "CAD", "MXN"]
     */
    @Column(name = "supported_currencies", columnDefinition = "JSON")
    private String supportedCurrencies;
    
    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<TaxRule> taxRules = new ArrayList<>();
    
    /**
     * Check if currency is supported in this region
     */
    public boolean supportsCurrency(String currencyCode) {
        if (supportedCurrencies == null) {
            return currencyCode.equals(defaultCurrency);
        }
        return supportedCurrencies.contains(currencyCode);
    }
}
