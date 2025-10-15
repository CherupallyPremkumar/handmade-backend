package com.homebase.ecom.entity;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "hm_wishlist_item")
public class WishlistItemEntity extends BaseJpaEntity {

    private String wishlistId;

    private String productVariantId;
    private String notes;


    public String getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(String wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
