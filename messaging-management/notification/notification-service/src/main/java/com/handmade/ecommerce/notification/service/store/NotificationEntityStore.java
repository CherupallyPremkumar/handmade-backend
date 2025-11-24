package com.handmade.ecommerce.notification.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.notification.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.notification.configuration.dao.NotificationRepository;
import java.util.Optional;

public class NotificationEntityStore implements EntityStore<Notification>{
    @Autowired private NotificationRepository notificationRepository;

	@Override
	public void store(Notification entity) {
        notificationRepository.save(entity);
	}

	@Override
	public Notification retrieve(String id) {
        Optional<Notification> entity = notificationRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Notification with ID " + id);
	}

}
