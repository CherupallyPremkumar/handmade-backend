package com.handmade.ecommerce.promotion;

import com.handmade.ecommerce.promotion.entity.PromotionRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PromotionRule
 * Generated from entity file
 */
@Repository
public interface PromotionRuleRepository extends JpaRepository<PromotionRule, String> {
    
    List<PromotionRule> findByPromotionId(String promotionId);
    List<PromotionRule> findByRuleType(String ruleType);
    List<PromotionRule> findByRuleKey(String ruleKey);
    List<PromotionRule> findByRulePriority(String rulePriority);
}
