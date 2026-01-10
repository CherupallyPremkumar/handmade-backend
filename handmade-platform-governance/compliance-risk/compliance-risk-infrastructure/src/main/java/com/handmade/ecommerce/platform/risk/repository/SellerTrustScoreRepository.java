package com.handmade.ecommerce.platform.risk;

import com.handmade.ecommerce.platform.risk.entity.SellerTrustScore;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerTrustScore
 * Generated from entity file
 */
@Repository
public interface SellerTrustScoreRepository extends JpaRepository<SellerTrustScore, String> {
    
    List<SellerTrustScore> findBySellerId(String sellerId);
}
