package com.handmade.ecommerce.cmsentry.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.cms.model.CMSEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.cmsentry.configuration.dao.CMSEntryRepository;
import java.util.Optional;

public class CMSEntryEntityStore implements EntityStore<CMSEntry>{
    @Autowired private CMSEntryRepository cmsentryRepository;

	@Override
	public void store(CMSEntry entity) {
        cmsentryRepository.save(entity);
	}

	@Override
	public CMSEntry retrieve(String id) {
        Optional<CMSEntry> entity = cmsentryRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find CMSEntry with ID " + id);
	}

}
