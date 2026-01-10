package com.handmade.ecommerce.promotion.pricing.engine;

import com.handmade.ecommerce.promotion.pricing.engine.entity.PricingRuleCondition;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PricingRuleCondition
 * Generated from entity file
 */
@Repository
public interface PricingRuleConditionRepository extends JpaRepository<PricingRuleCondition, String> {
    
    List<PricingRuleCondition> findByRuleId(String ruleId);
}
