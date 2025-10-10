package com.homebase.admin.repository;

import com.homebase.admin.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    
    List<WishlistItem> findByWishlistId(Long wishlistId);
    
    Optional<WishlistItem> findByWishlistIdAndProductId(Long wishlistId, Long productId);
    
    @Query("SELECT wi FROM WishlistItem wi WHERE wi.product.id = :productId AND wi.wishlist.tenantId = :tenantId")
    List<WishlistItem> findWishlistItemsContainingProduct(@Param("productId") Long productId, @Param("tenantId") String tenantId);
    
    void deleteByWishlistId(Long wishlistId);
}
