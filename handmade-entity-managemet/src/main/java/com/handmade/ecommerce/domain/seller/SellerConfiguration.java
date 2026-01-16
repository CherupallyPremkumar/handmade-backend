package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SellerConfiguration - Seller preferences and settings
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_configuration")
public class SellerConfiguration extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false, unique = true)
    private String sellerId;

    @Column(name = "auto_approve_orders")
    private Boolean autoApproveOrders = false;

    @Column(name = "allow_cod")
    private Boolean allowCod = false;

    @Column(name = "return_window_days")
    private Integer returnWindowDays = 7;

    @Column(name = "tax_inclusive_pricing")
    private Boolean taxInclusivePricing = true;

    @Column(name = "default_warehouse_id", length = 50)
    private String defaultWarehouseId;
}
