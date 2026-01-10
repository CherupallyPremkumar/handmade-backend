package com.handmade.ecommerce.inventory.vendor;

import com.handmade.ecommerce.inventory.vendor.entity.VendorPoLine;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for VendorPoLine
 * Generated from entity file
 */
@Repository
public interface VendorPoLineRepository extends JpaRepository<VendorPoLine, String> {
    
    List<VendorPoLine> findByPoId(String poId);
}
