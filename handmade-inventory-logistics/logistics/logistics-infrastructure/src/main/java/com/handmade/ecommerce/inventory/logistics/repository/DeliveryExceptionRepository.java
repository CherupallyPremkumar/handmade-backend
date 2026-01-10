package com.handmade.ecommerce.inventory.logistics;

import com.handmade.ecommerce.inventory.logistics.entity.DeliveryException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for DeliveryException
 * Generated from entity file
 */
@Repository
public interface DeliveryExceptionRepository extends JpaRepository<DeliveryException, String> {
    
    List<DeliveryException> findByTrackingNumber(String trackingNumber);
    List<DeliveryException> findByExceptionCode(String exceptionCode);
    List<DeliveryException> findByResolutionStatus(String resolutionStatus);
}
