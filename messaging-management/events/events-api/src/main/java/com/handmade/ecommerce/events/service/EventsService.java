package com.handmade.ecommerce.events.service;

import com.handmade.ecommerce.events.model.Events;

public interface EventsService {
	// Define your interface here
    public Events save(Events events);
    public Events retrieve(String id);
}
