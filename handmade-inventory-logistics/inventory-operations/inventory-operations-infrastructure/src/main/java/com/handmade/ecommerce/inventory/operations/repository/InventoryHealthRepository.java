package com.handmade.ecommerce.inventory.operations;

import com.handmade.ecommerce.inventory.operations.entity.InventoryHealth;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for InventoryHealth
 * Generated from entity file
 */
@Repository
public interface InventoryHealthRepository extends JpaRepository<InventoryHealth, String> {
    
    List<InventoryHealth> findBySellerId(String sellerId);
    List<InventoryHealth> findByFulfillmentNodeId(String fulfillmentNodeId);
}
