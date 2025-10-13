package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Notification;
import com.homebase.ecom.repository.NotificationRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationEntityStore implements EntityStore<Notification> {
    
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void store(Notification domainNotification) {
    }

    @Override
    public Notification retrieve(String id) {
        return null;
    }
}
