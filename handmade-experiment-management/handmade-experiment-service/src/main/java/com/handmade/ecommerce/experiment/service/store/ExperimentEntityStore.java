package com.handmade.ecommerce.experiment.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.experiment.model.Experiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.experiment.configuration.dao.ExperimentRepository;
import java.util.Optional;

public class ExperimentEntityStore implements EntityStore<Experiment>{
    @Autowired private ExperimentRepository experimentRepository;

	@Override
	public void store(Experiment entity) {
        experimentRepository.save(entity);
	}

	@Override
	public Experiment retrieve(String id) {
        Optional<Experiment> entity = experimentRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Experiment with ID " + id);
	}

}
