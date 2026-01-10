package com.handmade.ecommerce.policy.domain;

import com.handmade.ecommerce.policy.domain.entity.PerformanceThreshold;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PerformanceThreshold
 * Generated from entity file
 */
@Repository
public interface PerformanceThresholdRepository extends JpaRepository<PerformanceThreshold, String> {
    
    List<PerformanceThreshold> findByViolationType(PerformanceViolationType violationType);
}
