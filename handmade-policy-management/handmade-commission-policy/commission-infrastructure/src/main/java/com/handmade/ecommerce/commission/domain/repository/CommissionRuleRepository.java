package com.handmade.ecommerce.commission.domain;

import com.handmade.ecommerce.commission.domain.entity.CommissionRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CommissionRule
 * Generated from entity file
 */
@Repository
public interface CommissionRuleRepository extends JpaRepository<CommissionRule, String> {
    
    List<CommissionRule> findByRuleName(String ruleName);
    List<CommissionRule> findByFeeType(CommissionFeeType feeType);
}
