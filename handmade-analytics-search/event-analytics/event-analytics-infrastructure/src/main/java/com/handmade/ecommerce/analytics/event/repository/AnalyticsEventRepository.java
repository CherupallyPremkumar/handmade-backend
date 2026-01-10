package com.handmade.ecommerce.analytics.event;

import com.handmade.ecommerce.analytics.event.entity.AnalyticsEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for AnalyticsEvent
 * Generated from entity file
 */
@Repository
public interface AnalyticsEventRepository extends JpaRepository<AnalyticsEvent, String> {
    
    List<AnalyticsEvent> findByEventType(String eventType);
    List<AnalyticsEvent> findBySessionId(String sessionId);
    List<AnalyticsEvent> findByUserId(String userId);
    List<AnalyticsEvent> findByEntityId(String entityId);
    List<AnalyticsEvent> findByEntityType(String entityType);
}
