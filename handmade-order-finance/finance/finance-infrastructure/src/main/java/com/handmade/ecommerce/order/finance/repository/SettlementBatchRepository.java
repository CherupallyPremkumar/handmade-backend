package com.handmade.ecommerce.order.finance;

import com.handmade.ecommerce.order.finance.entity.SettlementBatch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SettlementBatch
 * Generated from entity file
 */
@Repository
public interface SettlementBatchRepository extends JpaRepository<SettlementBatch, String> {
    
    Optional<SettlementBatch> findByBatchReference(String batchReference);
    List<SettlementBatch> findByStatus(String status);

    // Existence checks
    boolean existsByBatchReference(String batchReference);
}
