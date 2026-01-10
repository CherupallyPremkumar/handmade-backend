package com.handmade.ecommerce.inventory.logistics;

import com.handmade.ecommerce.inventory.logistics.entity.WarehouseZone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for WarehouseZone
 * Generated from entity file
 */
@Repository
public interface WarehouseZoneRepository extends JpaRepository<WarehouseZone, String> {
    
    List<WarehouseZone> findByWarehouseId(String warehouseId);
    List<WarehouseZone> findByZoneCode(String zoneCode);
    List<WarehouseZone> findByZoneType(String zoneType);
    List<WarehouseZone> findByIsActive(Boolean isActive);
}
