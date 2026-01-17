package com.handmade.ecommerce.notificationqueue.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.notification.model.NotificationQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.notificationqueue.configuration.dao.NotificationQueueRepository;
import java.util.Optional;

public class NotificationQueueEntityStore implements EntityStore<NotificationQueue>{
    @Autowired private NotificationQueueRepository notificationqueueRepository;

	@Override
	public void store(NotificationQueue entity) {
        notificationqueueRepository.save(entity);
	}

	@Override
	public NotificationQueue retrieve(String id) {
        Optional<NotificationQueue> entity = notificationqueueRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find NotificationQueue with ID " + id);
	}

}
