package com.handmade.ecommerce.domain.finance;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_tax_calculation")
public class TaxCalculation extends BaseJpaEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "order_line_id", length = 36, nullable = false)
    private String orderLineId;

    @Column(name = "tax_code", length = 50, nullable = false)
    private String taxCode;

    @Column(name = "taxable_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal taxableAmount;

    @Column(name = "tax_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    @Column(name = "tax_rate", precision = 5, scale = 4, nullable = false)
    private BigDecimal taxRate;

    @Column(name = "jurisdiction", length = 100)
    private String jurisdiction;
}
