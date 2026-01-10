package com.handmade.ecommerce.platform.notification;

import com.handmade.ecommerce.platform.notification.entity.UserPreference;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for UserPreference
 * Generated from entity file
 */
@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, String> {
    
    List<UserPreference> findByUserId(String userId);
    List<UserPreference> findByNotificationType(String notificationType);
    List<UserPreference> findByIsEnabled(Boolean isEnabled);
}
