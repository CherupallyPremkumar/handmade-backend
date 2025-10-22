package com.homebase.ecom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "hm_price")
public class PriceEntity extends MultiTenantStateEntity {


    @Column(name = "price")
    private String price;
    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage=BigDecimal.ZERO;
    @Column(name = "product_variant_id")
    private String productVariantId;
    @Column(name = "sale_amount")
    private Double saleAmount;
    @Column(name = "currency")
    private String currency;

    public String getPrice() {
        return price;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public String getProductVariantId() {
        return productVariantId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String price() {
        return price;
    }

    public PriceEntity setPrice(String price) {
        this.price = price;
        return this;
    }

    public String productVariantId() {
        return productVariantId;
    }

    public PriceEntity setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
        return this;
    }

    public BigDecimal discountPercentage() {
        return discountPercentage;
    }

    public PriceEntity setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
        return this;
    }
    public Double getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(Double saleAmount) {
        this.saleAmount = saleAmount;
    }
}
