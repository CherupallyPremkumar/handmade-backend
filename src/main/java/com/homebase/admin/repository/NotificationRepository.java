package com.homebase.admin.repository;

import com.homebase.admin.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByTenantIdOrderByCreatedAtDesc(String tenantId);
    
    Long countByTenantIdAndReadFalse(String tenantId);
}
