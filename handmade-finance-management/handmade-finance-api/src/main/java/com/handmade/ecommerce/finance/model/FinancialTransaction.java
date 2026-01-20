package com.handmade.ecommerce.finance.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_transaction")
public class FinancialTransaction extends AbstractJpaStateEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "type", length = 50, nullable = false)
    private String type; // PAYMENT, REFUND, CHARGE, PAYOUT

    @Column(name = "amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode;

    @Column(name = "reference_type", length = 50)
    private String referenceType;

    @Column(name = "reference_id", length = 36)
    private String referenceId;

    @Column(name = "posted_date", nullable = false)
    private Date postedDate;
}
