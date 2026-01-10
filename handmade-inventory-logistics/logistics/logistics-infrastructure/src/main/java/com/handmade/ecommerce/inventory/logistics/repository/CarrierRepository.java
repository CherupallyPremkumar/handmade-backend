package com.handmade.ecommerce.inventory.logistics;

import com.handmade.ecommerce.inventory.logistics.entity.Carrier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Carrier
 * Generated from entity file
 */
@Repository
public interface CarrierRepository extends JpaRepository<Carrier, String> {
    
    Optional<Carrier> findByCarrierName(String carrierName);
    Optional<Carrier> findByCarrierCode(String carrierCode);
    List<Carrier> findByApiIntegrationType(String apiIntegrationType);
    List<Carrier> findByIsActive(Boolean isActive);

    // Existence checks
    boolean existsByCarrierName(String carrierName);
    boolean existsByCarrierCode(String carrierCode);
}
