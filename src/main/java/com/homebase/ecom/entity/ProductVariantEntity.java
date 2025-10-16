package com.homebase.ecom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Table(name = "hm_product_variant")
public class ProductVariantEntity extends AbstractJpaStateEntity {

    private String productId;
    private String artisanId;
    private String sku;
    private String displayName;
    private String description;
    private String color;
    private String size;
    private String attributes;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return displayName;
    }

    public void setName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getArtisanId() {
        return artisanId;
    }

    public void setArtisanId(String artisanId) {
        this.artisanId = artisanId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
