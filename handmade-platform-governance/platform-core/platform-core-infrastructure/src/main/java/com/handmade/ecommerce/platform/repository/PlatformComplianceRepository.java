package com.handmade.ecommerce.platform;

import com.handmade.ecommerce.platform.entity.PlatformCompliance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PlatformCompliance
 * Generated from entity file
 */
@Repository
public interface PlatformComplianceRepository extends JpaRepository<PlatformCompliance, String> {
    
    List<PlatformCompliance> findByPlatformId(String platformId);
    List<PlatformCompliance> findByComplianceCode(String complianceCode);
}
