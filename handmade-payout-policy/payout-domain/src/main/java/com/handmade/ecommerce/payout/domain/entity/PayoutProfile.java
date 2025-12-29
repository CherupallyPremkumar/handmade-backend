package com.handmade.ecommerce.payout.domain.entity;

import org.chenile.jpautils.entity.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Payout Profile for a Seller.
 * Stores preferred payout method, frequency, and account details.
 */
@Entity
@Table(name = "payout_profile")
@Getter
@Setter
@ToString
public class PayoutProfile extends BaseJpaEntity {

    @Column(name = "seller_id", nullable = false, length = 36, unique = true)
    private String sellerId;

    @Column(name = "preferred_frequency", length = 20)
    private String preferredFrequency; // DAILY, WEEKLY, MONTHLY

    @Column(name = "payout_method", length = 50)
    private String payoutMethod; // BANK_TRANSFER, STRIPE, PAYPAL

    @Override
    protected String getPrefix() {
        return "PAYOUT_PROF";
    }
}
