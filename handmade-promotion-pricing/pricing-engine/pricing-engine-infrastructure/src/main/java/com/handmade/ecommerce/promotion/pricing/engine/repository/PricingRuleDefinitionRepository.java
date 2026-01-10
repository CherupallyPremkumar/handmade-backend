package com.handmade.ecommerce.promotion.pricing.engine;

import com.handmade.ecommerce.promotion.pricing.engine.entity.PricingRuleDefinition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PricingRuleDefinition
 * Generated from entity file
 */
@Repository
public interface PricingRuleDefinitionRepository extends JpaRepository<PricingRuleDefinition, String> {
    
    Optional<PricingRuleDefinition> findByRuleKey(String ruleKey);
    List<PricingRuleDefinition> findByRuleName(String ruleName);
    List<PricingRuleDefinition> findByRuleType(String ruleType);
    List<PricingRuleDefinition> findByStatus(String status);

    // Existence checks
    boolean existsByRuleKey(String ruleKey);
}
