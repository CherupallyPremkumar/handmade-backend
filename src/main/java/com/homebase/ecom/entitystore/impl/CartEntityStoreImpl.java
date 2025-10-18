package com.homebase.ecom.entitystore.impl;

import com.homebase.ecom.domain.Cart;
import com.homebase.ecom.entity.CartEntity;
import com.homebase.ecom.entitystore.CartEntityStore;
import com.homebase.ecom.repository.CartRepository;
import org.chenile.utils.entity.service.EntityStore;

public class CartEntityStoreImpl  implements EntityStore<Cart>, CartEntityStore<Cart> {
    private final CartRepository cartRepository;

    public CartEntityStoreImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    public Cart findByCustomerId(String customerId)
    {
        CartEntity jpaCartEntity = cartRepository.findByCustomerId(customerId);
        if (jpaCartEntity != null) {
            return convertToDomainModel(jpaCartEntity);
        }
        return null;
    }
    /**
     * Store Cart domain model to database.
     * Converts domain model to JPA entity and persists it.
     *
     * @param domainCart Cart domain model to store
     */
    @Override
    public void store(Cart domainCart) {
        CartEntity jpaCartEntity = convertToJpaEntity(domainCart);
        cartRepository.save(jpaCartEntity);

        // Set ID back to domain model if it was newly created
        if (domainCart.getId() == null) {
            domainCart.setId(jpaCartEntity.getId());
        }
    }

    /**
     * Retrieve Cart domain model from database by ID.
     *
     * @param id Cart ID
     * @return Cart domain model or null if not found
     */
    @Override
    public Cart retrieve(String id) {
        return cartRepository.findById(id)
                .map(this::convertToDomainModel)
                .orElse(null);
    }

    /**
     * Convert Cart domain model to CartEntity JPA entity.
     *
     * @param domain Cart domain model
     * @return CartEntity JPA entity
     */
    private CartEntity convertToJpaEntity(Cart domain) {
        CartEntity jpa = new CartEntity();

        // Set ID if exists (for updates)
        if (domain.getId() != null) {
            jpa.setId(domain.getId());
        }

        // Map domain fields to JPA entity
        jpa.setCustomerId(domain.getCustomerId());
        jpa.setTxn(domain.getTxn());
        jpa.setTaxAmount(domain.getTaxAmount());
        jpa.setTotalAmount(domain.getTotalAmount());
        jpa.setDiscount(domain.getDiscount());
        jpa.setTransactionAmount(domain.getTransactionAmount());

        // Save state to JPA entity (from AbstractExtendedStateEntity)
        if (domain.getCurrentState() != null) {
            jpa.setCurrentState(domain.getCurrentState());
        }

        return jpa;
    }

    /**
     * Convert CartEntity JPA entity to Cart domain model.
     *
     * @param jpa CartEntity JPA entity
     * @return Cart domain model
     */
    private Cart convertToDomainModel(CartEntity jpa) {
        Cart domain = new Cart();

        // Map JPA entity fields to domain model
        domain.setId(jpa.getId());
        domain.setCustomerId(jpa.getCustomerId());
        domain.setTxn(jpa.getTxn());
        domain.setTaxAmount(jpa.getTaxAmount());
        domain.setTotalAmount(jpa.getTotalAmount());
        domain.setDiscount(jpa.getDiscount());
        domain.setTransactionAmount(jpa.getTransactionAmount());

        // Set state from JPA entity (to AbstractExtendedStateEntity)
        if (jpa.getCurrentState() != null) {
            domain.setCurrentState(jpa.getCurrentState());
        }

        return domain;
    }
}
