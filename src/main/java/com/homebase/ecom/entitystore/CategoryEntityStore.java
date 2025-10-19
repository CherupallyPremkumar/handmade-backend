package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Category;
import com.homebase.ecom.entity.CategoryEntity;
import com.homebase.ecom.repository.CategoryRepository;
import org.chenile.utils.entity.service.EntityStore;

public class CategoryEntityStore implements EntityStore<Category> {
    
    private final CategoryRepository categoryRepository;

    public CategoryEntityStore(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void store(Category domain) {
        CategoryEntity entity = convertToEntity(domain);
        CategoryEntity saved = categoryRepository.save(entity);
        if (domain.getId() == null) {
            domain.setId(saved.getId());
        }
    }

    @Override
    public Category retrieve(String id) {
        return categoryRepository.findById(id)
                .map(this::convertToDomain)
                .orElse(null);
    }
    
    private CategoryEntity convertToEntity(Category domain) {
        CategoryEntity entity = new CategoryEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setName(domain.getName());
        entity.setSlug(domain.getSlug());
        entity.setDescription(domain.getDescription());
        entity.setProductCount(domain.getProductCount());
        return entity;
    }
    
    private Category convertToDomain(CategoryEntity entity) {
        Category domain = new Category();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setSlug(entity.getSlug());
        domain.setDescription(entity.getDescription());
        domain.setProductCount(entity.getProductCount());
        return domain;
    }
}
