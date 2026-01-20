package com.handmade.ecommerce.seller.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.util.Date;

/**
 * SellerPaymentMethod - Seller payout methods
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_payment_method")
public class SellerPaymentMethod extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "payment_info_id", length = 36)
    private String paymentInfoId;

    @Column(name = "external_provider", length = 50)
    private String externalProvider;

    @Column(name = "external_method_id", length = 255)
    private String externalMethodId;

    @Column(name = "last4", length = 4)
    private String last4;

    @Column(name = "account_holder_name", length = 255)
    private String accountHolderName;

    @Column(name = "bank_name", length = 255)
    private String bankName;

    @Column(name = "paypal_email", length = 255)
    private String paypalEmail;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @Column(name = "status", length = 20)
    private String status = "ACTIVE";

    @Column(name = "added_at")
    private Date addedAt;
}
