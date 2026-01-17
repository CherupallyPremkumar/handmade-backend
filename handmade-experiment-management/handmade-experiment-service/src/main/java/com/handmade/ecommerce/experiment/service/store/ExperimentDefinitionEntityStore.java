package com.handmade.ecommerce.experiment.service.store;

import com.handmade.ecommerce.experiment.model.ExperimentDefinition;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.experiment.configuration.dao.ExperimentDefinitionRepository;
import java.util.Optional;

public class ExperimentDefinitionEntityStore implements EntityStore<ExperimentDefinition>{
    @Autowired private ExperimentDefinitionRepository experimentRepository;

	@Override
	public void store(ExperimentDefinition entity) {
        experimentRepository.save(entity);
	}

	@Override
	public ExperimentDefinition retrieve(String id) {
        Optional<ExperimentDefinition> entity = experimentRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find ExperimentDefinition with ID " + id);
	}

}
