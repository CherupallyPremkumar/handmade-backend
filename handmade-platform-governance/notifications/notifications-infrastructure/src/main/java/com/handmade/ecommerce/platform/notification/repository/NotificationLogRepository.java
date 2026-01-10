package com.handmade.ecommerce.platform.notification;

import com.handmade.ecommerce.platform.notification.entity.NotificationLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for NotificationLog
 * Generated from entity file
 */
@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, String> {
    
    List<NotificationLog> findByUserId(String userId);
    List<NotificationLog> findByTemplateId(String templateId);
    List<NotificationLog> findByStatus(String status);
    List<NotificationLog> findByProviderReferenceId(String providerReferenceId);
}
