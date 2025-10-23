package com.homebase.ecom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "hm_price")
public class PriceEntity extends MultiTenantStateEntity {


    @Column(name = "product_variant_id")
    private String productVariantId;

}
