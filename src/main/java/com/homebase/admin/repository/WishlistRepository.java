package com.homebase.admin.repository;

import com.homebase.admin.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    
    Optional<Wishlist> findByCustomerIdAndTenantId(Long customerId, String tenantId);
    
    List<Wishlist> findByTenantId(String tenantId);
    
    @Query("SELECT w FROM Wishlist w JOIN w.items wi WHERE wi.product.id = :productId AND w.tenantId = :tenantId")
    List<Wishlist> findWishlistsContainingProduct(@Param("productId") Long productId, @Param("tenantId") String tenantId);
}
