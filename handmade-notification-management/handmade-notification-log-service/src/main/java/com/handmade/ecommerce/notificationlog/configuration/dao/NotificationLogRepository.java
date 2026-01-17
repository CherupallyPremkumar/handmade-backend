package com.handmade.ecommerce.notificationlog.configuration.dao;

import com.handmade.ecommerce.notification.model.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface NotificationLogRepository extends JpaRepository<NotificationLog,String> {}
