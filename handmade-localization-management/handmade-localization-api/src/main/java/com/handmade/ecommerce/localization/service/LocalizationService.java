package com.handmade.ecommerce.localization.service;

import com.handmade.ecommerce.localization.model.Localization;

public interface LocalizationService {
	// Define your interface here
    public Localization save(Localization localization);
    public Localization retrieve(String id);
}
