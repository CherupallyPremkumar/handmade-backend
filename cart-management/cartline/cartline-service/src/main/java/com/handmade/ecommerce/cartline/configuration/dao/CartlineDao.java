package com.handmade.ecommerce.cartline.configuration.dao;

import com.handmade.ecommerce.cartline.model.Cartline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Cartline entity (Amazon-style, loosely coupled)
 */
@Repository
public interface CartlineDao extends JpaRepository<Cartline, String> {
    
    /**
     * Find all cart items for a cart
     */
    List<Cartline> findByCartId(String cartId);
    
    /**
     * Find specific variant in cart
     */
    Optional<Cartline> findByCartIdAndVariantId(String cartId, String variantId);
    
    /**
     * Delete all items in cart
     */
    void deleteByCartId(String cartId);
}
