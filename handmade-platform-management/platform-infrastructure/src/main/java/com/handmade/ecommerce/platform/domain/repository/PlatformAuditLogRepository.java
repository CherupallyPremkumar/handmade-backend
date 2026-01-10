package com.handmade.ecommerce.platform.domain;

import com.handmade.ecommerce.platform.domain.entity.PlatformAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PlatformAuditLog
 * Generated from entity file
 */
@Repository
public interface PlatformAuditLogRepository extends JpaRepository<PlatformAuditLog, String> {
    
    // No auto-generated query methods
}
