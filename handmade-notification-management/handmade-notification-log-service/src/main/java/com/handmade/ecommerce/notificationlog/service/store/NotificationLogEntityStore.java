package com.handmade.ecommerce.notificationlog.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.notification.model.NotificationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.notificationlog.configuration.dao.NotificationLogRepository;
import java.util.Optional;

public class NotificationLogEntityStore implements EntityStore<NotificationLog>{
    @Autowired private NotificationLogRepository notificationlogRepository;

	@Override
	public void store(NotificationLog entity) {
        notificationlogRepository.save(entity);
	}

	@Override
	public NotificationLog retrieve(String id) {
        Optional<NotificationLog> entity = notificationlogRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find NotificationLog with ID " + id);
	}

}
