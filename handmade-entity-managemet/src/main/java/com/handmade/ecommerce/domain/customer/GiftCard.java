package com.handmade.ecommerce.domain.customer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * GiftCard - Gift card vouchers managed by STM (ISSUED → ACTIVATED → REDEEMED → EXPIRED)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_gift_card")
public class GiftCard extends AbstractJpaStateEntity {

    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    @Column(name = "customer_id", length = 36)
    private String customerId;

    @Column(name = "initial_value", precision = 19, scale = 4, nullable = false)
    private BigDecimal initialValue;

    @Column(name = "current_value", precision = 19, scale = 4, nullable = false)
    private BigDecimal currentValue;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode;

    @Column(name = "expiration_date")
    private Date expirationDate;
}
