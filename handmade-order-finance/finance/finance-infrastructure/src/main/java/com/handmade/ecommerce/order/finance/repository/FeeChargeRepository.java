package com.handmade.ecommerce.order.finance;

import com.handmade.ecommerce.order.finance.entity.FeeCharge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for FeeCharge
 * Generated from entity file
 */
@Repository
public interface FeeChargeRepository extends JpaRepository<FeeCharge, String> {
    
    List<FeeCharge> findByTransactionId(String transactionId);
    List<FeeCharge> findByFeeType(String feeType);
}
