package com.handmade.ecommerce.inventory.operations;

import com.handmade.ecommerce.inventory.operations.entity.FulfillmentBin;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for FulfillmentBin
 * Generated from entity file
 */
@Repository
public interface FulfillmentBinRepository extends JpaRepository<FulfillmentBin, String> {
    
    List<FulfillmentBin> findByFulfillmentNodeId(String fulfillmentNodeId);
    List<FulfillmentBin> findByBinCode(String binCode);
}
