package com.handmade.ecommerce.platform;

import com.handmade.ecommerce.platform.entity.PlatformActivityLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PlatformActivityLog
 * Generated from entity file
 */
@Repository
public interface PlatformActivityLogRepository extends JpaRepository<PlatformActivityLog, String> {
    
    List<PlatformActivityLog> findByActivityName(String activityName);
}
