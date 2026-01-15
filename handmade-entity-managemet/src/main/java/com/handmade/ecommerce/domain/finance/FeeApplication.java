package com.handmade.ecommerce.domain.finance;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_fee_application")
public class FeeApplication extends BaseJpaEntity {

    @Column(name = "transaction_id", length = 36, nullable = false)
    private String transactionId;

    @Column(name = "fee_definition_id", length = 36, nullable = false)
    private String feeDefinitionId;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode;
}
