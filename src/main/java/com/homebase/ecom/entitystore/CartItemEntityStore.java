package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.entity.CartItemEntity;
import com.homebase.ecom.repository.CartItemRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Entity Store for CartItem domain model.
 * Handles conversion between CartItem domain model and CartItemEntity JPA entity.
 */

public interface CartItemEntityStore extends EntityStore<CartItem> {


    List<CartItem> findAllCartItemsByCartId(String cartId);
}
