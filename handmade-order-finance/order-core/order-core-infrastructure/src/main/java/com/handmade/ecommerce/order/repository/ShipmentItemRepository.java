package com.handmade.ecommerce.order;

import com.handmade.ecommerce.order.entity.ShipmentItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ShipmentItem
 * Generated from entity file
 */
@Repository
public interface ShipmentItemRepository extends JpaRepository<ShipmentItem, String> {
    
    List<ShipmentItem> findByShipmentId(String shipmentId);
    List<ShipmentItem> findByOrderLineId(String orderLineId);
}
