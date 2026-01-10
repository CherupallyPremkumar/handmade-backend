package com.handmade.ecommerce.order;

import com.handmade.ecommerce.order.entity.Shipment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for Shipment
 * Generated from entity file
 */
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {
    
    List<Shipment> findByOrderId(String orderId);
    List<Shipment> findBySellerId(String sellerId);
    List<Shipment> findByFulfillmentNodeId(String fulfillmentNodeId);
    List<Shipment> findByStatus(String status);
    List<Shipment> findByTrackingNumber(String trackingNumber);

    // STM State queries
    @Query("SELECT e FROM Shipment e WHERE e.state.stateId = :stateId")
    List<Shipment> findByStateId(@Param("stateId") String stateId);

    @Query("SELECT e FROM Shipment e WHERE e.state.flowId = :flowId")
    List<Shipment> findByFlowId(@Param("flowId") String flowId);

    @Query("SELECT e FROM Shipment e WHERE e.state.flowId = :flowId AND e.state.stateId = :stateId")
    List<Shipment> findByFlowIdAndStateId(@Param("flowId") String flowId, @Param("stateId") String stateId);
}
