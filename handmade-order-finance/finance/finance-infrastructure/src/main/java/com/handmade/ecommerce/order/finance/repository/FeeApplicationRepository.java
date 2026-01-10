package com.handmade.ecommerce.order.finance;

import com.handmade.ecommerce.order.finance.entity.FeeApplication;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for FeeApplication
 * Generated from entity file
 */
@Repository
public interface FeeApplicationRepository extends JpaRepository<FeeApplication, String> {
    
    List<FeeApplication> findByTransactionId(String transactionId);
    List<FeeApplication> findByFeeDefinitionId(String feeDefinitionId);
    List<FeeApplication> findByCurrencyCode(String currencyCode);
}
