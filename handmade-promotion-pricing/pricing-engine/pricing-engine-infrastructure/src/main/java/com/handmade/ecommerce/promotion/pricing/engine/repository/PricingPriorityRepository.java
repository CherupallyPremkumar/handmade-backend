package com.handmade.ecommerce.promotion.pricing.engine;

import com.handmade.ecommerce.promotion.pricing.engine.entity.PricingPriority;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PricingPriority
 * Generated from entity file
 */
@Repository
public interface PricingPriorityRepository extends JpaRepository<PricingPriority, String> {
    
    List<PricingPriority> findByRuleId(String ruleId);
    List<PricingPriority> findByPriorityScore(String priorityScore);
}
