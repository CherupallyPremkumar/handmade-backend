package com.homebase.ecom.mybatis.dashboard;

import java.math.BigDecimal;

public class ProductList {

    private Long internalId;
    private String displayName;
    private String description;
    private BigDecimal basePrice;
    private Long catId;
    private String sku;
    private Long productId;

    private String createdTime;
    public String quantityAvailable;
    public String quantityOnHand;
    public String location_name;
    public String thumbnail;

    public Long getInternalId() {
        return internalId;
    }

    public ProductList setInternalId(Long internalId) {
        this.internalId = internalId;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ProductList setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductList setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public ProductList setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public Long getCatId() {
        return catId;
    }

    public ProductList setCatId(Long catId) {
        this.catId = catId;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public ProductList setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public ProductList setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public String getQuantityAvailable() {
        return quantityAvailable;
    }

    public ProductList setQuantityAvailable(String quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
        return this;
    }

    public String getQuantityOnHand() {
        return quantityOnHand;
    }

    public ProductList setQuantityOnHand(String quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
        return this;
    }

    public String getLocation_name() {
        return location_name;
    }

    public ProductList setLocation_name(String location_name) {
        this.location_name = location_name;
        return this;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public ProductList setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ProductList setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }
}
