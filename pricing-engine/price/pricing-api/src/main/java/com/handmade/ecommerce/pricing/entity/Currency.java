package com.handmade.ecommerce.pricing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Currency Entity
 * Represents supported currencies
 */
@Entity
@Table(name = "hm_currency")
@Getter
@Setter
public class Currency {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "code", nullable = false, unique = true, length = 3)
    private String code; // USD, EUR, INR, etc. (ISO 4217)
    
    @Column(name = "name", nullable = false, length = 100)
    private String name; // US Dollar, Euro, Indian Rupee
    
    @Column(name = "symbol", nullable = false, length = 10)
    private String symbol; // $, €, ₹
    
    @Column(name = "decimal_places", nullable = false)
    private Integer decimalPlaces = 2;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
