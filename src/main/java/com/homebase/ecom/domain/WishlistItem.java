package com.homebase.ecom.domain;

import org.chenile.utils.entity.model.AbstractExtendedStateEntity;

public class WishlistItem extends MultiTenantExtendedStateEntity {

    private Wishlist wishlist;
    private Product product;

    private Integer desiredQuantity; // optional

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getDesiredQuantity() {
        return desiredQuantity;
    }

    public void setDesiredQuantity(Integer desiredQuantity) {
        this.desiredQuantity = desiredQuantity;
    }
}
