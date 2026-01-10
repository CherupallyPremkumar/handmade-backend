package com.handmade.ecommerce.platform.event;

import com.handmade.ecommerce.platform.event.entity.EventSubscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for EventSubscription
 * Generated from entity file
 */
@Repository
public interface EventSubscriptionRepository extends JpaRepository<EventSubscription, String> {
    
    List<EventSubscription> findByEventType(String eventType);
    List<EventSubscription> findByIsActive(Boolean isActive);
}
