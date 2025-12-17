package com.handmade.ecommerce.catalog.service.integration;

import java.math.BigDecimal;

/**
 * ACL DTO: Represents product data required by Catalog Service
 */
public class ExternalProductDto {
    private String id;
    private String name;
    private BigDecimal price;
    private boolean active;

    // Constructors
    public ExternalProductDto() {
    }

    public ExternalProductDto(String id, String name, BigDecimal price, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
