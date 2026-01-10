package com.handmade.ecommerce.platform.risk;

import com.handmade.ecommerce.platform.risk.entity.FraudCase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for FraudCase
 * Generated from entity file
 */
@Repository
public interface FraudCaseRepository extends JpaRepository<FraudCase, String> {
    
    List<FraudCase> findByReferenceId(String referenceId);
    List<FraudCase> findByStatus(String status);
    List<FraudCase> findByInvestigatorId(String investigatorId);
}
