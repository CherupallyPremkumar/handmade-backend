package com.handmade.ecommerce.platform;

import com.handmade.ecommerce.platform.entity.PlatformAuditLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PlatformAuditLog
 * Generated from entity file
 */
@Repository
public interface PlatformAuditLogRepository extends JpaRepository<PlatformAuditLog, String> {
    
    List<PlatformAuditLog> findByPlatformId(String platformId);
    List<PlatformAuditLog> findByEventType(String eventType);
    List<PlatformAuditLog> findByActorId(String actorId);
}
