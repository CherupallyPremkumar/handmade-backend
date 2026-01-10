package com.handmade.ecommerce.order.finance;

import com.handmade.ecommerce.order.finance.entity.FeeDefinition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for FeeDefinition
 * Generated from entity file
 */
@Repository
public interface FeeDefinitionRepository extends JpaRepository<FeeDefinition, String> {
    
    Optional<FeeDefinition> findByFeeCode(String feeCode);
    List<FeeDefinition> findByFeeType(String feeType);
    List<FeeDefinition> findByIsActive(Boolean isActive);

    // Existence checks
    boolean existsByFeeCode(String feeCode);
}
