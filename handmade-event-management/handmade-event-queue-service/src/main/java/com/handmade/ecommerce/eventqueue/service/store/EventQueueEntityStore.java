package com.handmade.ecommerce.eventqueue.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.event.model.EventQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.eventqueue.configuration.dao.EventQueueRepository;
import java.util.Optional;

public class EventQueueEntityStore implements EntityStore<EventQueue>{
    @Autowired private EventQueueRepository eventqueueRepository;

	@Override
	public void store(EventQueue entity) {
        eventqueueRepository.save(entity);
	}

	@Override
	public EventQueue retrieve(String id) {
        Optional<EventQueue> entity = eventqueueRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find EventQueue with ID " + id);
	}

}
