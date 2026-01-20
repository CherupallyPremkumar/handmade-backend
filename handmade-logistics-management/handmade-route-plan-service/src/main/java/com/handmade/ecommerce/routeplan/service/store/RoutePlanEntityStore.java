package com.handmade.ecommerce.routeplan.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.logistics.model.RoutePlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.routeplan.configuration.dao.RoutePlanRepository;
import java.util.Optional;

public class RoutePlanEntityStore implements EntityStore<RoutePlan>{
    @Autowired private RoutePlanRepository routeplanRepository;

	@Override
	public void store(RoutePlan entity) {
        routeplanRepository.save(entity);
	}

	@Override
	public RoutePlan retrieve(String id) {
        Optional<RoutePlan> entity = routeplanRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find RoutePlan with ID " + id);
	}

}
