package com.handmade.ecommerce.order;

import com.handmade.ecommerce.order.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for Order
 * Generated from entity file
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    
    List<Order> findByPlatformId(String platformId);
    List<Order> findByCustomerId(String customerId);
    List<Order> findByDisplayId(String displayId);
    List<Order> findByCurrencyCode(String currencyCode);
    List<Order> findByStatus(String status);
    List<Order> findByPurchaseOrderNumber(String purchaseOrderNumber);

    // STM State queries
    @Query("SELECT e FROM Order e WHERE e.state.stateId = :stateId")
    List<Order> findByStateId(@Param("stateId") String stateId);

    @Query("SELECT e FROM Order e WHERE e.state.flowId = :flowId")
    List<Order> findByFlowId(@Param("flowId") String flowId);

    @Query("SELECT e FROM Order e WHERE e.state.flowId = :flowId AND e.state.stateId = :stateId")
    List<Order> findByFlowIdAndStateId(@Param("flowId") String flowId, @Param("stateId") String stateId);
}
