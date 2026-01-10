package com.handmade.ecommerce.inventory.operations;

import com.handmade.ecommerce.inventory.operations.entity.PickTask;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PickTask
 * Generated from entity file
 */
@Repository
public interface PickTaskRepository extends JpaRepository<PickTask, String> {
    
    List<PickTask> findByFulfillmentNodeId(String fulfillmentNodeId);
    List<PickTask> findByOrderId(String orderId);
    List<PickTask> findByAssignedToUserId(String assignedToUserId);
    List<PickTask> findByStatus(String status);
    List<PickTask> findByPriority(String priority);
}
