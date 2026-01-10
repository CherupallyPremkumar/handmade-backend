package com.handmade.ecommerce.inventory.logistics;

import com.handmade.ecommerce.inventory.logistics.entity.ShippingLabel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ShippingLabel
 * Generated from entity file
 */
@Repository
public interface ShippingLabelRepository extends JpaRepository<ShippingLabel, String> {
    
    List<ShippingLabel> findByShipmentId(String shipmentId);
    List<ShippingLabel> findByCarrierId(String carrierId);
    Optional<ShippingLabel> findByTrackingNumber(String trackingNumber);
    List<ShippingLabel> findByStatus(String status);

    // Existence checks
    boolean existsByTrackingNumber(String trackingNumber);
}
