package com.handmade.ecommerce.notificationtemplate.configuration.dao;

import com.handmade.ecommerce.notification.model.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate,String> {}
