package com.homebase.admin.repository;

import com.homebase.admin.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    
    Optional<Cart> findByCustomerIdAndTenantIdAndStatus(String customerId, String tenantId, Cart.CartStatus status);
    
    List<Cart> findByTenantId(String tenantId);
    
    @Query("SELECT c FROM Cart c JOIN c.cartItems ci WHERE ci.product.id = :productId AND c.tenantId = :tenantId")
    List<Cart> findCartsContainingProduct(@Param("productId") Long productId, @Param("tenantId") String tenantId);
}
