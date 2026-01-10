package com.handmade.ecommerce.platform.notification;

import com.handmade.ecommerce.platform.notification.entity.NotificationQueue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for NotificationQueue
 * Generated from entity file
 */
@Repository
public interface NotificationQueueRepository extends JpaRepository<NotificationQueue, String> {
    
    List<NotificationQueue> findByRecipientId(String recipientId);
    List<NotificationQueue> findByRecipientType(String recipientType);
    List<NotificationQueue> findByTemplateId(String templateId);
    List<NotificationQueue> findByStatus(String status);
}
