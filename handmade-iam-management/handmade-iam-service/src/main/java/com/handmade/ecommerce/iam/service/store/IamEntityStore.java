package com.handmade.ecommerce.iam.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.iam.model.Iam;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.iam.configuration.dao.IamRepository;
import java.util.Optional;

public class IamEntityStore implements EntityStore<Iam>{
    @Autowired private IamRepository iamRepository;

	@Override
	public void store(Iam entity) {
        iamRepository.save(entity);
	}

	@Override
	public Iam retrieve(String id) {
        Optional<Iam> entity = iamRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Iam with ID " + id);
	}

}
