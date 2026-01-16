package com.handmade.ecommerce.domain.customer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * Subscription - Customer subscription managed by STM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_subscription")
public class Subscription extends AbstractJpaStateEntity {

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "plan_type", length = 50)
    private String planType; // MONTHLY, ANNUAL

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "next_billing_date")
    private Date nextBillingDate;

    @Column(name = "auto_renew")
    private Boolean autoRenew = true;
}
