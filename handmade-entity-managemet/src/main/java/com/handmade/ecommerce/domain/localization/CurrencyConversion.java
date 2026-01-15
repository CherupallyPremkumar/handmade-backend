package com.handmade.ecommerce.domain.localization;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Currency Conversion - Exchange rates between currencies
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_currency_conversion")
public class CurrencyConversion extends BaseJpaEntity {

    @Column(name = "from_currency", length = 3, nullable = false)
    private String fromCurrency;

    @Column(name = "to_currency", length = 3, nullable = false)
    private String toCurrency;

    @Column(name = "exchange_rate", precision = 19, scale = 6, nullable = false)
    private BigDecimal exchangeRate;

    @Column(name = "effective_date")
    private Date effectiveDate;
}
