package com.handmade.ecommerce.messaging.service;

import com.handmade.ecommerce.messaging.model.Messaging;

public interface MessagingService {
	// Define your interface here
    public Messaging save(Messaging messaging);
    public Messaging retrieve(String id);
}
