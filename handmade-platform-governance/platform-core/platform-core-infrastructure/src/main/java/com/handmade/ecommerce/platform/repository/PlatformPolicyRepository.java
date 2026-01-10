package com.handmade.ecommerce.platform;

import com.handmade.ecommerce.platform.entity.PlatformPolicy;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PlatformPolicy
 * Generated from entity file
 */
@Repository
public interface PlatformPolicyRepository extends JpaRepository<PlatformPolicy, String> {
    
    List<PlatformPolicy> findByPlatformId(String platformId);
    List<PlatformPolicy> findByPolicyType(String policyType);
}
