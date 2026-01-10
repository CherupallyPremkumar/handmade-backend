package com.handmade.ecommerce.seller.offer;

import com.handmade.ecommerce.seller.offer.entity.PriceRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PriceRule
 * Generated from entity file
 */
@Repository
public interface PriceRuleRepository extends JpaRepository<PriceRule, String> {
    
    List<PriceRule> findByName(String name);
    List<PriceRule> findByRuleType(String ruleType);
    List<PriceRule> findByIsActive(Boolean isActive);
}
