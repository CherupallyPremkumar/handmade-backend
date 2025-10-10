package com.homebase.admin.repository;

import com.homebase.admin.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByCartId(Long cartId);
    
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    
    @Query("SELECT ci FROM CartItem ci WHERE ci.product.id = :productId AND ci.cart.tenantId = :tenantId")
    List<CartItem> findCartItemsContainingProduct(@Param("productId") Long productId, @Param("tenantId") String tenantId);
    
    void deleteByCartId(Long cartId);
}
