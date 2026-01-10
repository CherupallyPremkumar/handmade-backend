package com.handmade.ecommerce.seller.offer;

import com.handmade.ecommerce.seller.offer.entity.BuyBoxEligibility;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for BuyBoxEligibility
 * Generated from entity file
 */
@Repository
public interface BuyBoxEligibilityRepository extends JpaRepository<BuyBoxEligibility, String> {
    
    List<BuyBoxEligibility> findByOfferId(String offerId);
    List<BuyBoxEligibility> findByProductId(String productId);
}
