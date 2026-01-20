package com.handmade.ecommerce.gdprrequest.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.governance.model.GDPRRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.gdprrequest.configuration.dao.GDPRRequestRepository;
import java.util.Optional;

public class GDPRRequestEntityStore implements EntityStore<GDPRRequest>{
    @Autowired private GDPRRequestRepository gdprrequestRepository;

	@Override
	public void store(GDPRRequest entity) {
        gdprrequestRepository.save(entity);
	}

	@Override
	public GDPRRequest retrieve(String id) {
        Optional<GDPRRequest> entity = gdprrequestRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find GDPRRequest with ID " + id);
	}

}
