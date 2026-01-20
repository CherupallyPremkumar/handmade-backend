package com.handmade.ecommerce.translation.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.localization.model.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.translation.configuration.dao.TranslationRepository;
import java.util.Optional;

public class TranslationEntityStore implements EntityStore<Translation>{
    @Autowired private TranslationRepository translationRepository;

	@Override
	public void store(Translation entity) {
        translationRepository.save(entity);
	}

	@Override
	public Translation retrieve(String id) {
        Optional<Translation> entity = translationRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Translation with ID " + id);
	}

}
