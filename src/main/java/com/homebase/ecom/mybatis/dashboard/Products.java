package com.homebase.ecom.mybatis.dashboard;

import java.math.BigDecimal;
import java.util.List;

public class Products {
    private Long internalId;
    private String displayName;
    private String description;
    private BigDecimal basePrice;
    private Long catId;
    private String sku;
    private Long productId;
    private String thumbnail;

    public String getCreatedTime() {
        return createdTime;
    }

    public Products setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    private String createdTime;
    public List<InventoryDetails> inventoryDetails;


    public List<InventoryDetails> getInventoryDetails() {
        return inventoryDetails;
    }

    public Products setInventoryDetails(List<InventoryDetails> inventoryDetails) {
        this.inventoryDetails = inventoryDetails;
        return this;
    }

    public Long getInternalId() {
        return internalId;
    }

    public Products setInternalId(Long internalId) {
        this.internalId = internalId;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Products setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Products setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public Products setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public Long getCatId() {
        return catId;
    }

    public Products setCatId(Long catId) {
        this.catId = catId;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public Products setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public Products setProductId(Long productId) {
        this.productId = productId;
        return this;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public Products setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }
}
