package com.handmade.ecommerce.auditprocess.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.governance.model.AuditProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.auditprocess.configuration.dao.AuditProcessRepository;
import java.util.Optional;

public class AuditProcessEntityStore implements EntityStore<AuditProcess>{
    @Autowired private AuditProcessRepository auditprocessRepository;

	@Override
	public void store(AuditProcess entity) {
        auditprocessRepository.save(entity);
	}

	@Override
	public AuditProcess retrieve(String id) {
        Optional<AuditProcess> entity = auditprocessRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find AuditProcess with ID " + id);
	}

}
