package com.handmade.ecommerce.domain.finance;

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
@Table(name = "hm_payout")
public class Payout extends AbstractJpaStateEntity {

    @Column(name = "seller_id", length = 36)
    private String sellerId;

    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "bank_reference_id", length = 100)
    private String bankReferenceId;

    @Column(name = "payout_date")
    private Date payoutDate;
}
