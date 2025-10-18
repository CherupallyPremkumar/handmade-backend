package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.entity.CartItemEntity;
import com.homebase.ecom.repository.CartItemRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.stereotype.Component;

/**
 * Entity Store for CartItem domain model.
 * Handles conversion between CartItem domain model and CartItemEntity JPA entity.
 */
@Component
public class CartItemEntityStore implements EntityStore<CartItem> {

    private final CartItemRepository cartItemRepository;

    public CartItemEntityStore(CartItemRepository cartItemRepository) {
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
        CartItemEntity jpaCartItemEntity = convertToJpaEntity(domainCartItem);
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
                .map(this::convertToDomainModel)
                .orElse(null);
    }

    /**
     * Convert CartItem domain model to CartItemEntity JPA entity.
     *
     * @param domain CartItem domain model
     * @return CartItemEntity JPA entity
     */
    private CartItemEntity convertToJpaEntity(CartItem domain) {
        CartItemEntity jpa = new CartItemEntity();
        
        // Set ID if exists (for updates)
        if (domain.getId() != null) {
            jpa.setId(domain.getId());
        }
        
        // Map domain fields to JPA entity
        jpa.setCartId(domain.getCartId());
        jpa.setProductVariantId(domain.getProductVariantId());
        jpa.setQuantity(domain.getQuantity());
        jpa.setSnapshotPrice(domain.getSnapshotPrice());
        jpa.setWasOnSale(domain.getWasOnSale());
        jpa.setOriginalPrice(domain.getOriginalPrice());
        
        // Calculate total amount (quantity * snapshot price)
        if (domain.getSnapshotPrice() != null && domain.getQuantity() > 0) {
            jpa.setTotalAmount(domain.getSnapshotPrice().multiply(
                java.math.BigDecimal.valueOf(domain.getQuantity())
            ));
        }
        
        // Save state to JPA entity (from AbstractExtendedStateEntity)
        if (domain.getCurrentState() != null) {
            jpa.setCurrentState(domain.getCurrentState());
        }
        
        // Save sub-tenant ID (from MultiTenantExtendedStateEntity)
        if (domain.getSubTenantId() != null) {
            jpa.setSubTenantId(domain.getSubTenantId());
        }
        
        return jpa;
    }

    /**
     * Convert CartItemEntity JPA entity to CartItem domain model.
     *
     * @param jpa CartItemEntity JPA entity
     * @return CartItem domain model
     */
    private CartItem convertToDomainModel(CartItemEntity jpa) {
        CartItem domain = new CartItem();
        
        // Map JPA entity fields to domain model
        domain.setId(jpa.getId());
        domain.setCartId(jpa.getCartId());
        domain.setProductVariantId(jpa.getProductVariantId());
        domain.setQuantity(jpa.getQuantity());
        domain.setSnapshotPrice(jpa.getSnapshotPrice());
        domain.setWasOnSale(jpa.getWasOnSale());
        domain.setOriginalPrice(jpa.getOriginalPrice());
        
        // Set state from JPA entity (to AbstractExtendedStateEntity)
        if (jpa.getCurrentState() != null) {
            domain.setCurrentState(jpa.getCurrentState());
        }
        
        // Set sub-tenant ID (to MultiTenantExtendedStateEntity)
        if (jpa.getSubTenantId() != null) {
            domain.setSubTenantId(jpa.getSubTenantId());
        }
        
        return domain;
    }
}
