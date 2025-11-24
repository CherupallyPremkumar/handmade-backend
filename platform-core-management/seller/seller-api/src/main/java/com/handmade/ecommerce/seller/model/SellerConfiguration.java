package com.handmade.ecommerce.seller.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.io.Serializable;

@Entity
@Table(name = "hm_seller_configuration")
public class SellerConfiguration extends BaseJpaEntity {


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Column(name = "auto_approve_orders")
    private Boolean autoApproveOrders = false;

    @Column(name = "allow_cod")
    private Boolean allowCOD = false;

    @Column(name = "return_window_days")
    private Integer returnWindowDays = 7;

    @Column(name = "tax_inclusive_pricing")
    private Boolean taxInclusivePricing = true;

    @Column(name = "default_warehouse_id", length = 50)
    private String defaultWarehouseId;


}