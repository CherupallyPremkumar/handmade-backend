package com.handmade.ecommerce.eventqueue.configuration.dao;

import com.handmade.ecommerce.event.model.EventSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventSubscriptionRepository extends JpaRepository<EventSubscription, String> {
    List<EventSubscription> findByEventTypeAndIsActiveTrue(String eventType);
}
