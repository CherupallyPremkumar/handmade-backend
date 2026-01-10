package com.handmade.ecommerce.inventory.logistics;

import com.handmade.ecommerce.inventory.logistics.entity.DeliveryAttempt;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for DeliveryAttempt
 * Generated from entity file
 */
@Repository
public interface DeliveryAttemptRepository extends JpaRepository<DeliveryAttempt, String> {
    
    List<DeliveryAttempt> findByTrackingNumber(String trackingNumber);
}
