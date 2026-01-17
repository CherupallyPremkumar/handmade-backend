package com.handmade.ecommerce.compliancedocument.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.risk.model.ComplianceDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.compliancedocument.configuration.dao.ComplianceDocumentRepository;
import java.util.Optional;

public class ComplianceDocumentEntityStore implements EntityStore<ComplianceDocument>{
    @Autowired private ComplianceDocumentRepository compliancedocumentRepository;

	@Override
	public void store(ComplianceDocument entity) {
        compliancedocumentRepository.save(entity);
	}

	@Override
	public ComplianceDocument retrieve(String id) {
        Optional<ComplianceDocument> entity = compliancedocumentRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find ComplianceDocument with ID " + id);
	}

}
