package com.handmade.ecommerce.analytics.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.analytics.model.MetricDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.analytics.configuration.dao.MetricDefinitionRepository;
import java.util.Optional;

public class MetricDefinitionEntityStore implements EntityStore<MetricDefinition>{
    @Autowired private MetricDefinitionRepository analyticsRepository;

	@Override
	public void store(MetricDefinition entity) {
        analyticsRepository.save(entity);
	}

	@Override
	public MetricDefinition retrieve(String id) {
        Optional<MetricDefinition> entity = analyticsRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find MetricDefinition with ID " + id);
	}

}
