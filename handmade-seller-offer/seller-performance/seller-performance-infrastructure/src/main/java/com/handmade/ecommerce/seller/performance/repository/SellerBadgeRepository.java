package com.handmade.ecommerce.seller.performance;

import com.handmade.ecommerce.seller.performance.entity.SellerBadge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerBadge
 * Generated from entity file
 */
@Repository
public interface SellerBadgeRepository extends JpaRepository<SellerBadge, String> {
    
    List<SellerBadge> findBySellerId(String sellerId);
    List<SellerBadge> findByBadgeCode(String badgeCode);
}
