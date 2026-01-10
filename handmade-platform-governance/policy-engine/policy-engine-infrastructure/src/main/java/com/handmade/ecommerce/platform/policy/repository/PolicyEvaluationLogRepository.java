package com.handmade.ecommerce.platform.policy;

import com.handmade.ecommerce.platform.policy.entity.PolicyEvaluationLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PolicyEvaluationLog
 * Generated from entity file
 */
@Repository
public interface PolicyEvaluationLogRepository extends JpaRepository<PolicyEvaluationLog, String> {
    
    List<PolicyEvaluationLog> findByDecisionId(String decisionId);
    List<PolicyEvaluationLog> findByRuleId(String ruleId);
}
