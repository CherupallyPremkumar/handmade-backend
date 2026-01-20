package com.handmade.ecommerce.localization.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * Currency - Represents a currency with exchange rates
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_currency")
public class Currency extends BaseJpaEntity {

    @Column(name = "currency_code", length = 3, nullable = false, unique = true)
    private String currencyCode;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "symbol", length = 10)
    private String symbol;

    @Column(name = "exchange_rate_to_usd", precision = 19, scale = 6)
    private BigDecimal exchangeRateToUsd;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
