package com.handmade.ecommerce.domain.customer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * CustomerWallet - Customer digital wallet for credits/refunds
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_customer_wallet")
public class CustomerWallet extends BaseJpaEntity {

    @Column(name = "customer_id", length = 36, nullable = false, unique = true)
    private String customerId;

    @Column(name = "balance", precision = 19, scale = 4, nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
