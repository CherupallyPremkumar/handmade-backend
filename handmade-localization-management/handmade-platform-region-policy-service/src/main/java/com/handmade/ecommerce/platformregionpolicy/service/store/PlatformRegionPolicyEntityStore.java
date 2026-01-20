package com.handmade.ecommerce.platformregionpolicy.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.localization.model.PlatformRegionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.platformregionpolicy.configuration.dao.PlatformRegionPolicyRepository;
import java.util.Optional;

public class PlatformRegionPolicyEntityStore implements EntityStore<PlatformRegionPolicy>{
    @Autowired private PlatformRegionPolicyRepository platformregionpolicyRepository;

	@Override
	public void store(PlatformRegionPolicy entity) {
        platformregionpolicyRepository.save(entity);
	}

	@Override
	public PlatformRegionPolicy retrieve(String id) {
        Optional<PlatformRegionPolicy> entity = platformregionpolicyRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PlatformRegionPolicy with ID " + id);
	}

}
