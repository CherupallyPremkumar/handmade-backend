package com.handmade.ecommerce.location.service;

import com.handmade.ecommerce.location.model.Location;

public interface LocationService {
	// Define your interface here
    public Location save(Location location);
    public Location retrieve(String id);
}
