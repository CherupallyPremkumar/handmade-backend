package com.handmade.ecommerce.platform.risk;

import com.handmade.ecommerce.platform.risk.entity.RiskSignal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for RiskSignal
 * Generated from entity file
 */
@Repository
public interface RiskSignalRepository extends JpaRepository<RiskSignal, String> {
    
    List<RiskSignal> findByEntityId(String entityId);
    List<RiskSignal> findByEntityType(String entityType);
    List<RiskSignal> findBySignalType(String signalType);
}
