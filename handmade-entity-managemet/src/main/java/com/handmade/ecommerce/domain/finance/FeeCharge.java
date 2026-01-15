package com.handmade.ecommerce.domain.finance;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_fee_charge")
public class FeeCharge extends BaseJpaEntity {

    @Column(name = "transaction_id", length = 36, nullable = false)
    private String transactionId;

    @Column(name = "fee_type", length = 50)
    private String feeType; // COMMISSION, FBA_FEE, SUBSCRIPTION

    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "description", length = 255)
    private String description;
}
