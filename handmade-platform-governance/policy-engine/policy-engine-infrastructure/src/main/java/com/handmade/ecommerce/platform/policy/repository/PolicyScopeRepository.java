package com.handmade.ecommerce.platform.policy;

import com.handmade.ecommerce.platform.policy.entity.PolicyScope;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PolicyScope
 * Generated from entity file
 */
@Repository
public interface PolicyScopeRepository extends JpaRepository<PolicyScope, String> {
    
    List<PolicyScope> findByRuleId(String ruleId);
}
