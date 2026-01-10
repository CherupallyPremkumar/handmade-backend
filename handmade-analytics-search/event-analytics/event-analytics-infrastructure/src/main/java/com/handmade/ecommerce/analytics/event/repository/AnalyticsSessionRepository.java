package com.handmade.ecommerce.analytics.event;

import com.handmade.ecommerce.analytics.event.entity.AnalyticsSession;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for AnalyticsSession
 * Generated from entity file
 */
@Repository
public interface AnalyticsSessionRepository extends JpaRepository<AnalyticsSession, String> {
    
    List<AnalyticsSession> findByUserId(String userId);
    Optional<AnalyticsSession> findBySessionToken(String sessionToken);
    List<AnalyticsSession> findByStatus(String status);
    List<AnalyticsSession> findByDeviceType(String deviceType);

    // Existence checks
    boolean existsBySessionToken(String sessionToken);
}
