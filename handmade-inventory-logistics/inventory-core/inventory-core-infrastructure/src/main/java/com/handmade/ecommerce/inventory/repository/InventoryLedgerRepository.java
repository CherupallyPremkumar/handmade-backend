package com.handmade.ecommerce.inventory;

import com.handmade.ecommerce.inventory.entity.InventoryLedger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for InventoryLedger
 * Generated from entity file
 */
@Repository
public interface InventoryLedgerRepository extends JpaRepository<InventoryLedger, String> {
    
    List<InventoryLedger> findByFulfillmentNodeId(String fulfillmentNodeId);
    List<InventoryLedger> findByReferenceId(String referenceId);
}
