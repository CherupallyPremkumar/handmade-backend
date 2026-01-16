package com.handmade.ecommerce.notification.configuration.dao;

import com.handmade.ecommerce.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface NotificationRepository extends JpaRepository<Notification,String> {}
