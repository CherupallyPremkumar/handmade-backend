package com.handmade.ecommerce.notificationqueue.configuration.dao;

import com.handmade.ecommerce.notification.model.NotificationQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface NotificationQueueRepository extends JpaRepository<NotificationQueue,String> {}
