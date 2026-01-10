package com.handmade.ecommerce.customer.wishlist;

import com.handmade.ecommerce.customer.wishlist.entity.Wishlist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Wishlist
 * Generated from entity file
 */
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, String> {
    
    List<Wishlist> findByCustomerId(String customerId);
    List<Wishlist> findByName(String name);
}
