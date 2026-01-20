package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SellerPaymentInfo - Seller payment account information
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_payment_info")
public class SellerPaymentInfo extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false, unique = true)
    private String sellerId;

    @Column(name = "preferred_method", length = 50)
    private String preferredMethod;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "is_default_active")
    private Boolean isDefaultActive = true;
}
