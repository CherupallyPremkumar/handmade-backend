package com.handmade.ecommerce.inventory.logistics;

import com.handmade.ecommerce.inventory.logistics.entity.RoutePlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for RoutePlan
 * Generated from entity file
 */
@Repository
public interface RoutePlanRepository extends JpaRepository<RoutePlan, String> {
    
    List<RoutePlan> findByDriverId(String driverId);
    List<RoutePlan> findByVehicleId(String vehicleId);
    List<RoutePlan> findByStatus(String status);
}
