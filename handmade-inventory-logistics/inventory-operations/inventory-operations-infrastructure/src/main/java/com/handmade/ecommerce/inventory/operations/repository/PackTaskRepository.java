package com.handmade.ecommerce.inventory.operations;

import com.handmade.ecommerce.inventory.operations.entity.PackTask;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PackTask
 * Generated from entity file
 */
@Repository
public interface PackTaskRepository extends JpaRepository<PackTask, String> {
    
    List<PackTask> findByFulfillmentNodeId(String fulfillmentNodeId);
    List<PackTask> findByPickTaskId(String pickTaskId);
    List<PackTask> findByShipmentId(String shipmentId);
    List<PackTask> findByStatus(String status);
}
