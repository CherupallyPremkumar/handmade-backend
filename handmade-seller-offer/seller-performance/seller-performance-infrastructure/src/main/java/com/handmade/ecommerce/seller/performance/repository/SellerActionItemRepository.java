package com.handmade.ecommerce.seller.performance;

import com.handmade.ecommerce.seller.performance.entity.SellerActionItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerActionItem
 * Generated from entity file
 */
@Repository
public interface SellerActionItemRepository extends JpaRepository<SellerActionItem, String> {
    
    List<SellerActionItem> findBySellerId(String sellerId);
    List<SellerActionItem> findByActionType(String actionType);
    List<SellerActionItem> findByStatus(String status);
}
