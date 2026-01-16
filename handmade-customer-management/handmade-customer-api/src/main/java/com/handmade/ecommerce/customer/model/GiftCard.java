package com.handmade.ecommerce.customer.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * GiftCard - Gift card vouchers managed by STM (ISSUED → ACTIVATED → REDEEMED →
 * EXPIRED)
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_gift_card")
public class GiftCard extends AbstractJpaStateEntity {

    @Column(name = "claim_code", length = 50, nullable = false, unique = true)
    private String code;

    @Column(name = "customer_id", length = 36)
    private String customerId;

    @Column(name = "initial_balance", precision = 19, scale = 4, nullable = false)
    private BigDecimal initialValue;

    @Column(name = "current_balance", precision = 19, scale = 4, nullable = false)
    private BigDecimal currentValue;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode;

    @Column(name = "expiry_date")
    private Date expirationDate;
}
