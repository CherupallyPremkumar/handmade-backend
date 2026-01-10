package com.handmade.ecommerce.finance.domain;

import com.handmade.ecommerce.finance.domain.entity.SettlementTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SettlementTransaction
 * Generated from entity file
 */
@Repository
public interface SettlementTransactionRepository extends JpaRepository<SettlementTransaction, String> {
    
    List<SettlementTransaction> findByType(Type type);
    List<SettlementTransaction> findByReferenceId(String referenceId);
}
