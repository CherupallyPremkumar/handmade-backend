package com.handmade.ecommerce.seller.performance;

import com.handmade.ecommerce.seller.performance.entity.SellerPerformance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerPerformance
 * Generated from entity file
 */
@Repository
public interface SellerPerformanceRepository extends JpaRepository<SellerPerformance, String> {
    
    List<SellerPerformance> findBySellerId(String sellerId);
    List<SellerPerformance> findByMetricName(String metricName);
    List<SellerPerformance> findByStatus(String status);
}
