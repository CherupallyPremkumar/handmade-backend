package com.handmade.ecommerce.content.localization.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_currency
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_currency")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Currency extends BaseJpaEntity {
    
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "symbol", length = 10)
    private String symbol;
    @Column(name = "exchange_rate_to_usd", precision = 19, scale = 6)
    private BigDecimal exchangeRateToUsd;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
