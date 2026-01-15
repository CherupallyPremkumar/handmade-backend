package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SellerPaymentMethod - Seller payout methods
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_payment_method")
public class SellerPaymentMethod extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "method_type", length = 50, nullable = false)
    private String methodType;

    @Column(name = "account_holder_name", length = 255)
    private String accountHolderName;

    @Column(name = "account_number_masked", length = 100)
    private String accountNumberMasked;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "is_verified")
    private Boolean isVerified = false;
}
