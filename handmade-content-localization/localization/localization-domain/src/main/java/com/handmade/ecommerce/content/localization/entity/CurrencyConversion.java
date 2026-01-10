package com.handmade.ecommerce.content.localization.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_currency_conversion
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_currency_conversion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CurrencyConversion extends BaseJpaEntity {
    
    @Column(name = "from_currency", nullable = false, length = 3)
    private String fromCurrency;
    @Column(name = "to_currency", nullable = false, length = 3)
    private String toCurrency;
    @Column(name = "exchange_rate", nullable = false, precision = 19, scale = 6)
    private BigDecimal exchangeRate;
    @Column(name = "effective_date")
    private Date effectiveDate;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
