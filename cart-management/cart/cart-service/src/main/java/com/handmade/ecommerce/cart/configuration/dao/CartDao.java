package com.handmade.ecommerce.cart.configuration.dao;

import com.handmade.ecommerce.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Cart entity (Amazon-style, no JPA relationships)
 */
@Repository
public interface CartDao extends JpaRepository<Cart, String> {

    /**
     * Find active cart by user ID
     */
    Optional<Cart> findByUserId(String userId);

    /**
     * Find active cart by session ID
     */
    Optional<Cart> findBySessionId(String sessionId);
}
