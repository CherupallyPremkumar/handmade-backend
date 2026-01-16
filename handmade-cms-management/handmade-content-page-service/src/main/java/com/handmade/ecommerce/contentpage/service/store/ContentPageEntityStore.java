package com.handmade.ecommerce.contentpage.service.store;

import com.handmade.ecommerce.cms.model.ContentPage;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.contentpage.configuration.dao.ContentPageRepository;
import java.util.Optional;

public class ContentPageEntityStore implements EntityStore<ContentPage>{
    @Autowired private ContentPageRepository contentpageRepository;

	@Override
	public void store(ContentPage entity) {
        contentpageRepository.save(entity);
	}

	@Override
	public ContentPage retrieve(String id) {
        Optional<ContentPage> entity = contentpageRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find ContentPage with ID " + id);
	}

}
