package com.handmade.ecommerce.customer.wishlist;

import com.handmade.ecommerce.customer.wishlist.entity.WishlistItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for WishlistItem
 * Generated from entity file
 */
@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, String> {
    
    List<WishlistItem> findByWishlistId(String wishlistId);
    List<WishlistItem> findByProductId(String productId);
    List<WishlistItem> findByPriority(String priority);
}
