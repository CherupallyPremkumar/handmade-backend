package com.handmade.ecommerce.limitdefinition.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.limit.model.LimitDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.limitdefinition.configuration.dao.LimitDefinitionRepository;
import java.util.Optional;

public class LimitDefinitionEntityStore implements EntityStore<LimitDefinition>{
    @Autowired private LimitDefinitionRepository limitdefinitionRepository;

	@Override
	public void store(LimitDefinition entity) {
        limitdefinitionRepository.save(entity);
	}

	@Override
	public LimitDefinition retrieve(String id) {
        Optional<LimitDefinition> entity = limitdefinitionRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find LimitDefinition with ID " + id);
	}

}
