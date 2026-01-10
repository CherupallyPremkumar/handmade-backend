package com.handmade.ecommerce.platform.risk;

import com.handmade.ecommerce.platform.risk.entity.FraudSignal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for FraudSignal
 * Generated from entity file
 */
@Repository
public interface FraudSignalRepository extends JpaRepository<FraudSignal, String> {
    
    List<FraudSignal> findBySignalType(String signalType);
    List<FraudSignal> findByUserId(String userId);
}
