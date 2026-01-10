package com.handmade.ecommerce.seller.performance;

import com.handmade.ecommerce.seller.performance.entity.SellerFeedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerFeedback
 * Generated from entity file
 */
@Repository
public interface SellerFeedbackRepository extends JpaRepository<SellerFeedback, String> {
    
    List<SellerFeedback> findBySellerId(String sellerId);
    List<SellerFeedback> findByCustomerId(String customerId);
    List<SellerFeedback> findByOrderId(String orderId);
}
