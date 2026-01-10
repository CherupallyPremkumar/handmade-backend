package com.handmade.ecommerce.customer.cart;

import com.handmade.ecommerce.customer.cart.entity.CartSavedForLater;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CartSavedForLater
 * Generated from entity file
 */
@Repository
public interface CartSavedForLaterRepository extends JpaRepository<CartSavedForLater, String> {
    
    List<CartSavedForLater> findByCustomerId(String customerId);
    List<CartSavedForLater> findByOfferId(String offerId);
    List<CartSavedForLater> findByProductId(String productId);
    List<CartSavedForLater> findByProductName(String productName);
    List<CartSavedForLater> findBySellerId(String sellerId);
    List<CartSavedForLater> findByCurrencyCode(String currencyCode);
}
