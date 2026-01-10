package com.handmade.ecommerce.promotion.pricing.engine;

import com.handmade.ecommerce.promotion.pricing.engine.entity.PricingRuleAction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PricingRuleAction
 * Generated from entity file
 */
@Repository
public interface PricingRuleActionRepository extends JpaRepository<PricingRuleAction, String> {
    
    List<PricingRuleAction> findByRuleId(String ruleId);
    List<PricingRuleAction> findByActionType(String actionType);
}
