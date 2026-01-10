package com.handmade.ecommerce.order;

import com.handmade.ecommerce.order.entity.ReturnRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for ReturnRequest
 * Generated from entity file
 */
@Repository
public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, String> {
    
    List<ReturnRequest> findByOrderId(String orderId);
    List<ReturnRequest> findByCustomerId(String customerId);
    List<ReturnRequest> findByStatus(String status);

    // STM State queries
    @Query("SELECT e FROM ReturnRequest e WHERE e.state.stateId = :stateId")
    List<ReturnRequest> findByStateId(@Param("stateId") String stateId);

    @Query("SELECT e FROM ReturnRequest e WHERE e.state.flowId = :flowId")
    List<ReturnRequest> findByFlowId(@Param("flowId") String flowId);

    @Query("SELECT e FROM ReturnRequest e WHERE e.state.flowId = :flowId AND e.state.stateId = :stateId")
    List<ReturnRequest> findByFlowIdAndStateId(@Param("flowId") String flowId, @Param("stateId") String stateId);
}
