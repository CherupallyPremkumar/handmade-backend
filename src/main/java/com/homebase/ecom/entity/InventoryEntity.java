package com.homebase.ecom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * Inventory entity with state management.
 */
@Entity
@Table(name = "hm_inventory")
public class InventoryEntity extends MultiTenantStateEntity {

    @Column(name = "quantity_on_hand")
    private int quantityOnHand;
    @NotNull
    @Column(name = "product_variant_id")
    private String productVariantId;
    @Column(name = "location_id")
    private String locationId;
    @Column(name = "quantity_committed")
    private int quantityCommitted;
    @Column(name = "quantity_available")
    private int quantityAvailable;
    @Column(name = "quantity_back_ordered")
    private int quantityBackOrdered;


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }

}
