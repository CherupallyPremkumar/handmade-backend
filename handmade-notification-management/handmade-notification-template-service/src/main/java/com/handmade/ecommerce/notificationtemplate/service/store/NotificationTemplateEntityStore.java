package com.handmade.ecommerce.notificationtemplate.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.notification.model.NotificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.notificationtemplate.configuration.dao.NotificationTemplateRepository;
import java.util.Optional;

public class NotificationTemplateEntityStore implements EntityStore<NotificationTemplate>{
    @Autowired private NotificationTemplateRepository notificationtemplateRepository;

	@Override
	public void store(NotificationTemplate entity) {
        notificationtemplateRepository.save(entity);
	}

	@Override
	public NotificationTemplate retrieve(String id) {
        Optional<NotificationTemplate> entity = notificationtemplateRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find NotificationTemplate with ID " + id);
	}

}
