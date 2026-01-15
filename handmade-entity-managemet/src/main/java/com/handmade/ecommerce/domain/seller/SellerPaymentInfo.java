package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SellerPaymentInfo - Seller payment account information
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_payment_info")
public class SellerPaymentInfo extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false, unique = true)
    private String sellerId;

    @Column(name = "payment_provider", length = 100)
    private String paymentProvider;

    @Column(name = "account_id", length = 255)
    private String accountId;

    @Column(name = "account_status", length = 50)
    private String accountStatus;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "payout_schedule", length = 50)
    private String payoutSchedule;
}
