package com.handmade.ecommerce.inventory.vendor;

import com.handmade.ecommerce.inventory.vendor.entity.VendorPo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for VendorPo
 * Generated from entity file
 */
@Repository
public interface VendorPoRepository extends JpaRepository<VendorPo, String> {
    
    Optional<VendorPo> findByPoNumber(String poNumber);
    List<VendorPo> findByVendorCode(String vendorCode);
    List<VendorPo> findByStatus(String status);
    List<VendorPo> findByDestinationNodeId(String destinationNodeId);

    // Existence checks
    boolean existsByPoNumber(String poNumber);
}
