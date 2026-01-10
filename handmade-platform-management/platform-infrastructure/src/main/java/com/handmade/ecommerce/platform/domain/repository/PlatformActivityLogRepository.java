package com.handmade.ecommerce.platform.domain;

import com.handmade.ecommerce.platform.domain.entity.PlatformActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PlatformActivityLog
 * Generated from entity file
 */
@Repository
public interface PlatformActivityLogRepository extends JpaRepository<PlatformActivityLog, String> {
    
    // No auto-generated query methods
}
