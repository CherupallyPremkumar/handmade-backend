package com.handmade.ecommerce.platform.notification;

import com.handmade.ecommerce.platform.notification.entity.NotificationTemplate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for NotificationTemplate
 * Generated from entity file
 */
@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, String> {
    
    Optional<NotificationTemplate> findByTemplateCode(String templateCode);
    List<NotificationTemplate> findByIsActive(Boolean isActive);

    // Existence checks
    boolean existsByTemplateCode(String templateCode);
}
