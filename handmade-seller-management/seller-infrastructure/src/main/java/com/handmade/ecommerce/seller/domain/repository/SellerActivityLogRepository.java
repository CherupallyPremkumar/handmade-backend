package com.handmade.ecommerce.seller.domain;

import com.handmade.ecommerce.seller.domain.entity.SellerActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerActivityLog
 * Generated from entity file
 */
@Repository
public interface SellerActivityLogRepository extends JpaRepository<SellerActivityLog, String> {
    
    // No auto-generated query methods
}
