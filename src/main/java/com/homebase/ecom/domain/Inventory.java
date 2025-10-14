package com.homebase.ecom.domain;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.chenile.utils.entity.model.AbstractExtendedStateEntity;


public class Inventory extends AbstractExtendedStateEntity {
    private int quantityOnHand;
    private String productVariantId;
    private String locationId;
    private int quantityCommitted;
    private int quantityAvailable;
    private int quantityBackOrdered;

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public String getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getQuantityCommitted() {
        return quantityCommitted;
    }

    public void setQuantityCommitted(int quantityCommitted) {
        this.quantityCommitted = quantityCommitted;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public int getQuantityBackOrdered() {
        return quantityBackOrdered;
    }

    public void setQuantityBackOrdered(int quantityBackOrdered) {
        this.quantityBackOrdered = quantityBackOrdered;
    }
}
