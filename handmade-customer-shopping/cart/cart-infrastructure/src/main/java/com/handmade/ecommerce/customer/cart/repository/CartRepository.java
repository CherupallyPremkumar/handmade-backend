package com.handmade.ecommerce.customer.cart;

import com.handmade.ecommerce.customer.cart.entity.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Cart
 * Generated from entity file
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    
    List<Cart> findByCustomerId(String customerId);
    List<Cart> findBySessionId(String sessionId);
    List<Cart> findByStatus(String status);
    List<Cart> findByCurrencyCode(String currencyCode);
    List<Cart> findByCartType(String cartType);
    List<Cart> findByMergedFromCartId(String mergedFromCartId);
    List<Cart> findByConvertedToOrderId(String convertedToOrderId);
}
