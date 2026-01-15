package com.handmade.ecommerce.domain.customer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Subscription - Customer subscription managed by STM
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_subscription")
public class Subscription extends AbstractJpaStateEntity {

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "plan_id", length = 36, nullable = false)
    private String planId;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "renewal_date")
    private Date renewalDate;

    @Column(name = "amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "auto_renew")
    private Boolean autoRenew = true;
}
