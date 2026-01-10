package com.handmade.ecommerce.content.localization;

import com.handmade.ecommerce.content.localization.entity.PlatformRegionPolicy;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PlatformRegionPolicy
 * Generated from entity file
 */
@Repository
public interface PlatformRegionPolicyRepository extends JpaRepository<PlatformRegionPolicy, String> {
    
    List<PlatformRegionPolicy> findByPlatformId(String platformId);
    List<PlatformRegionPolicy> findByRegionCode(String regionCode);
    List<PlatformRegionPolicy> findByPolicyId(String policyId);
    List<PlatformRegionPolicy> findByIsActive(Boolean isActive);
}
