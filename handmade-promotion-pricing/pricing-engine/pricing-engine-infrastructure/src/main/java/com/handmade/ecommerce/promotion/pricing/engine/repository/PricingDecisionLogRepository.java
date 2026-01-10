package com.handmade.ecommerce.promotion.pricing.engine;

import com.handmade.ecommerce.promotion.pricing.engine.entity.PricingDecisionLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PricingDecisionLog
 * Generated from entity file
 */
@Repository
public interface PricingDecisionLogRepository extends JpaRepository<PricingDecisionLog, String> {
    
    List<PricingDecisionLog> findByTransactionId(String transactionId);
}
