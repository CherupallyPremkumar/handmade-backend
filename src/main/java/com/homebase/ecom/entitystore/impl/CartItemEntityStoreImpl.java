package com.homebase.ecom.entitystore.impl;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.entity.CartItemEntity;
import com.homebase.ecom.entitystore.CartItemEntityStore;
import com.homebase.ecom.repository.CartItemRepository;
import org.chenile.utils.entity.service.EntityStore;

import java.util.List;

public class CartItemEntityStoreImpl implements CartItemEntityStore {
private final CartItemRepository cartItemRepository;

public CartItemEntityStoreImpl(CartItemRepository cartItemRepository) {
    this.cartItemRepository = cartItemRepository;
}

/**
 * Store CartItem domain model to database.
 * Converts domain model to JPA entity and persists it.
 *
 * @param domainCartItem CartItem domain model to store
 */
@Override
public void store(CartItem domainCartItem) {
    CartItemEntity jpaCartItemEntity = toEntity(domainCartItem);
    cartItemRepository.save(jpaCartItemEntity);

    // Set ID back to domain model if it was newly created
    if (domainCartItem.getId() == null) {
        domainCartItem.setId(jpaCartItemEntity.getId());
    }
}

/**
 * Retrieve CartItem domain model from database by ID.
 *
 * @param id CartItem ID
 * @return CartItem domain model or null if not found
 */
@Override
public CartItem retrieve(String id) {
    return cartItemRepository.findById(id)
            .map(this::toDomain)
            .orElse(null);
}

    /** Convert JPA entity to domain */
    public CartItem toDomain(CartItemEntity entity) {
        if (entity == null) return null;

        CartItem cartItem = new CartItem();
        cartItem.setId(entity.getId());
        cartItem.setCartId(entity.getCartId());
        cartItem.setProductVariantId(entity.getProductVariantId());
        cartItem.setQuantity(entity.getQuantity());
        cartItem.setSnapshotPrice(entity.getSnapshotPrice());
        cartItem.setSalePrice(entity.getSalePrice());
        cartItem.setOriginalPrice(entity.getOriginalPrice());
        cartItem.setTaxRate(entity.getTaxRate());
        cartItem.setTaxAmount(entity.getTaxAmount());
        cartItem.setTotalAmount(entity.getTotalAmount());
        cartItem.setSubTenantId(entity.getSubTenantId());
        cartItem.setCurrentState(entity.getCurrentState());
        cartItem.setCreatedTime(entity.getCreatedTime());
        cartItem.setCreatedBy(entity.getCreatedBy());
        cartItem.setLastModifiedTime(entity.getLastModifiedTime());
        cartItem.setLastModifiedBy(entity.getLastModifiedBy());

        return cartItem;
    }

    /** Convert domain model to JPA entity */
    public CartItemEntity toEntity(CartItem cartItem) {
        if (cartItem == null) return null;

        CartItemEntity entity = new CartItemEntity();
        entity.setId(cartItem.getId());
        entity.setCartId(cartItem.getCartId());
        entity.setProductVariantId(cartItem.getProductVariantId());
        entity.setQuantity(cartItem.getQuantity());
        entity.setSnapshotPrice(cartItem.getSnapshotPrice());
        entity.setSalePrice(cartItem.getSalePrice());
        entity.setOriginalPrice(cartItem.getOriginalPrice());
        entity.setTaxRate(cartItem.getTaxRate());
        entity.setTaxAmount(cartItem.getTaxAmount());
        entity.setTotalAmount(cartItem.getTotalAmount());
        entity.setSubTenantId(cartItem.getSubTenantId());
        entity.setCurrentState(cartItem.getCurrentState());
        entity.setCreatedTime(cartItem.getCreatedTime());
        entity.setCreatedBy(cartItem.getCreatedBy());
        entity.setLastModifiedTime(cartItem.getLastModifiedTime());
        entity.setLastModifiedBy(cartItem.getLastModifiedBy());

        return entity;
    }

    @Override
    public List<CartItem> findAllCartItemsByCartId(String cartId) {
        return cartItemRepository.findByCartId(cartId)
                .stream()
                .map(this::toDomain)
                .toList();
    }
}
