package com.handmade.ecommerce.integration.vendor;

import com.handmade.ecommerce.integration.vendor.entity.ExternalVendor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ExternalVendor
 * Generated from entity file
 */
@Repository
public interface ExternalVendorRepository extends JpaRepository<ExternalVendor, String> {
    
    List<ExternalVendor> findByVendorName(String vendorName);
    List<ExternalVendor> findByVendorType(String vendorType);
    List<ExternalVendor> findByIsActive(Boolean isActive);
}
