package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Tag;
import com.homebase.ecom.repository.TagRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagEntityStore implements EntityStore<Tag> {
    
    @Autowired
    private TagRepository tagRepository;
    
    @Override
    public void store(Tag domainTag) {
    }

    @Override
    public Tag retrieve(String id) {
        return null;
    }
}
