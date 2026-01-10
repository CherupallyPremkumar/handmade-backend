package com.handmade.ecommerce.promotion.adtech;

import com.handmade.ecommerce.promotion.adtech.entity.AdPerformance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for AdPerformance
 * Generated from entity file
 */
@Repository
public interface AdPerformanceRepository extends JpaRepository<AdPerformance, String> {
    
    List<AdPerformance> findBySponsoredProductId(String sponsoredProductId);
}
