package com.handmade.ecommerce.pricing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Exchange Rate Entity
 * Stores currency exchange rates for conversion
 */
@Entity
@Table(name = "hm_exchange_rate", indexes = {
    @Index(name = "idx_exchange_currencies", columnList = "from_currency,to_currency"),
    @Index(name = "idx_exchange_date", columnList = "effective_date DESC")
})
@Getter
@Setter
public class ExchangeRate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "from_currency", nullable = false, length = 3)
    private String fromCurrency;
    
    @Column(name = "to_currency", nullable = false, length = 3)
    private String toCurrency;
    
    @Column(name = "rate", nullable = false, precision = 10, scale = 6)
    private BigDecimal rate;
    
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;
    
    @Column(name = "source", length = 100)
    private String source; // API provider name
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * Convert amount using this exchange rate
     */
    public BigDecimal convert(BigDecimal amount) {
        return amount.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * Check if rate is expired (older than 24 hours)
     */
    public boolean isExpired() {
        return updatedAt.isBefore(LocalDateTime.now().minusHours(24));
    }
    
    @PrePersist
    @PreUpdate
    protected void onSave() {
        updatedAt = LocalDateTime.now();
        if (effectiveDate == null) {
            effectiveDate = LocalDate.now();
        }
    }
}
