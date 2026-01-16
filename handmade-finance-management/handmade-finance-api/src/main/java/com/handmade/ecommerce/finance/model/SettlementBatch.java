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
@Table(name = "hm_settlement_batch")
public class SettlementBatch extends AbstractJpaStateEntity {

    @Column(name = "batch_reference", length = 100, nullable = false, unique = true)
    private String batchReference;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "close_time")
    private Date closeTime;

    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
}
