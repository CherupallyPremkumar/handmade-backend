package com.handmade.ecommerce.inventory.operations;

import com.handmade.ecommerce.inventory.operations.entity.InventoryReservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for InventoryReservation
 * Generated from entity file
 */
@Repository
public interface InventoryReservationRepository extends JpaRepository<InventoryReservation, String> {
    
    List<InventoryReservation> findByInventoryItemId(String inventoryItemId);
    List<InventoryReservation> findByOrderId(String orderId);
    List<InventoryReservation> findByStatus(String status);
}
