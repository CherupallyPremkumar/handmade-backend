package com.handmade.ecommerce.inventory.inbound;

import com.handmade.ecommerce.inventory.inbound.entity.InboundShipmentItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for InboundShipmentItem
 * Generated from entity file
 */
@Repository
public interface InboundShipmentItemRepository extends JpaRepository<InboundShipmentItem, String> {
    
    List<InboundShipmentItem> findByShipmentId(String shipmentId);
}
