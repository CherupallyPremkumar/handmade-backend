package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Wishlist;
import com.homebase.ecom.repository.WishlistRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class WishlistEntityStore implements EntityStore<Wishlist> {
    
    private final WishlistRepository wishlistRepository;


    public WishlistEntityStore(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public void store(Wishlist entity) {

    }

    @Override
    public Wishlist retrieve(String id) {
        return null;
    }
}
