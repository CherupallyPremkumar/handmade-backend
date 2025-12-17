package com.handmade.ecommerce.catalog.service.impl;

import com.handmade.ecommerce.catalog.model.Collection;
import com.handmade.ecommerce.catalog.repository.CollectionRepository;
import com.handmade.ecommerce.catalog.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Override
    @Transactional
    public Collection createCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    @Override
    @Transactional
    public Collection updateCollection(String id, Collection collection) {
        Collection existing = collectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection not found: " + id));

        existing.setName(collection.getName());
        existing.setSlug(collection.getSlug());
        existing.setDescription(collection.getDescription());
        existing.setType(collection.getType());
        existing.setImageUrl(collection.getImageUrl());
        existing.setStartDate(collection.getStartDate());
        existing.setEndDate(collection.getEndDate());
        existing.setActive(collection.getActive());
        existing.setFeatured(collection.getFeatured());
        existing.setDisplayOrder(collection.getDisplayOrder());
        existing.setAutoUpdate(collection.getAutoUpdate());
        existing.setRuleExpression(collection.getRuleExpression());

        return collectionRepository.save(existing);
    }

    @Override
    public Optional<Collection> getCollectionById(String id) {
        return collectionRepository.findById(id);
    }

    @Override
    public List<Collection> getActiveCollections() {
        return collectionRepository.findByActiveTrue();
    }

    @Override
    public List<Collection> getDynamicCollections() {
        return collectionRepository.findAllActiveDynamicCollections();
    }

    @Override
    @Transactional
    public void deleteCollection(String id) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection not found: " + id));
        collection.setActive(false); // Soft delete
        collectionRepository.save(collection);
    }
}
