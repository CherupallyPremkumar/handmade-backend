package com.handmade.ecommerce.platform.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.platform.model.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.platform.configuration.dao.PlatformRepository;
import java.util.Optional;

public class PlatformEntityStore implements EntityStore<Platform>{
    @Autowired private PlatformRepository platformRepository;

	@Override
	public void store(Platform entity) {
        platformRepository.save(entity);
	}

	@Override
	public Platform retrieve(String id) {
        Optional<Platform> entity = platformRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Platform with ID " + id);
	}

}
