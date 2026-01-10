package com.handmade.ecommerce.policy.domain;

import com.handmade.ecommerce.policy.domain.entity.ListingPolicyRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ListingPolicyRule
 * Generated from entity file
 */
@Repository
public interface ListingPolicyRuleRepository extends JpaRepository<ListingPolicyRule, String> {
    
    List<ListingPolicyRule> findByRuleName(String ruleName);
    List<ListingPolicyRule> findByViolationType(ListingViolationType violationType);
}
