package com.handmade.ecommerce.platform.event;

import com.handmade.ecommerce.platform.event.entity.EventQueue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for EventQueue
 * Generated from entity file
 */
@Repository
public interface EventQueueRepository extends JpaRepository<EventQueue, String> {
    
    List<EventQueue> findByEventType(String eventType);
    List<EventQueue> findByStatus(String status);
}
