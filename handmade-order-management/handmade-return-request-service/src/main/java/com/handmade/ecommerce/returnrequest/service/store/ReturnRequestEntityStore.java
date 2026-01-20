package com.handmade.ecommerce.returnrequest.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.order.model.ReturnRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.returnrequest.configuration.dao.ReturnRequestRepository;
import java.util.Optional;

public class ReturnRequestEntityStore implements EntityStore<ReturnRequest>{
    @Autowired private ReturnRequestRepository returnrequestRepository;

	@Override
	public void store(ReturnRequest entity) {
        returnrequestRepository.save(entity);
	}

	@Override
	public ReturnRequest retrieve(String id) {
        Optional<ReturnRequest> entity = returnrequestRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find ReturnRequest with ID " + id);
	}

}
