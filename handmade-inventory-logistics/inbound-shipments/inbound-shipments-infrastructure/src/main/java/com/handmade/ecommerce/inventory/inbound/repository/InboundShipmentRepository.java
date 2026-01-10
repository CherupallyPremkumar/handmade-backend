package com.handmade.ecommerce.inventory.inbound;

import com.handmade.ecommerce.inventory.inbound.entity.InboundShipment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for InboundShipment
 * Generated from entity file
 */
@Repository
public interface InboundShipmentRepository extends JpaRepository<InboundShipment, String> {
    
    List<InboundShipment> findBySellerId(String sellerId);
    List<InboundShipment> findByDestinationNodeId(String destinationNodeId);
    List<InboundShipment> findByShipFromAddressId(String shipFromAddressId);
    List<InboundShipment> findByStatus(String status);
    List<InboundShipment> findByLabelPrepType(String labelPrepType);
    List<InboundShipment> findByCarrierName(String carrierName);
    List<InboundShipment> findByTrackingNumber(String trackingNumber);
}
