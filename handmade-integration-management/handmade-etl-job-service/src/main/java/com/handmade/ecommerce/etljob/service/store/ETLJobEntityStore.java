package com.handmade.ecommerce.etljob.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.integration.model.ETLJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.etljob.configuration.dao.ETLJobRepository;
import java.util.Optional;

public class ETLJobEntityStore implements EntityStore<ETLJob>{
    @Autowired private ETLJobRepository etljobRepository;

	@Override
	public void store(ETLJob entity) {
        etljobRepository.save(entity);
	}

	@Override
	public ETLJob retrieve(String id) {
        Optional<ETLJob> entity = etljobRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find ETLJob with ID " + id);
	}

}
