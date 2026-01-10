package com.handmade.ecommerce.inventory.operations;

import com.handmade.ecommerce.inventory.operations.entity.InventoryPosition;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for InventoryPosition
 * Generated from entity file
 */
@Repository
public interface InventoryPositionRepository extends JpaRepository<InventoryPosition, String> {
    
    List<InventoryPosition> findByWarehouseId(String warehouseId);
    List<InventoryPosition> findByProductId(String productId);
}
