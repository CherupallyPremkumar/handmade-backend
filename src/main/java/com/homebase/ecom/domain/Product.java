package com.homebase.ecom.domain;

import org.chenile.utils.entity.model.AbstractExtendedStateEntity;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



public class Product extends AbstractExtendedStateEntity {

    private String name;


    private String description;

    private BigDecimal price;

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    private boolean onSale;
    private BigDecimal salePrice;


    public BigDecimal getPrice() {
        return isOnSale() ? salePrice : price;
    }

    private Integer stock = 0;

// Support base64 images up to ~5MB
    private String imageUrl;

    private String category;


    private List<String> tags = new ArrayList<>();

    private Double rating = 0.0;

    private Boolean featured = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }
}
