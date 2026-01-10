package com.handmade.ecommerce.seller.performance;

import com.handmade.ecommerce.seller.performance.entity.SellerRecommendation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerRecommendation
 * Generated from entity file
 */
@Repository
public interface SellerRecommendationRepository extends JpaRepository<SellerRecommendation, String> {
    
    List<SellerRecommendation> findBySellerId(String sellerId);
    List<SellerRecommendation> findByRecommendationType(String recommendationType);
    List<SellerRecommendation> findByResourceId(String resourceId);
    List<SellerRecommendation> findByPriority(String priority);
    List<SellerRecommendation> findByStatus(String status);
}
