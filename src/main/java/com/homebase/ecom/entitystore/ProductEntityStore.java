package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Product;
import com.homebase.ecom.entity.ProductEntity;
import com.homebase.ecom.repository.ProductRepository;
import org.chenile.utils.entity.service.EntityStore;

public class ProductEntityStore implements EntityStore<Product> {
    
    private final ProductRepository productRepository;

    public ProductEntityStore(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void store(Product domain) {
        ProductEntity entity = convertToEntity(domain);
        ProductEntity saved = productRepository.save(entity);
        if (domain.getId() == null) {
            domain.setId(saved.getId());
        }
    }

    @Override
    public Product retrieve(String id) {
        return productRepository.findById(id)
                .map(this::convertToDomain)
                .orElse(null);
    }
    
    private ProductEntity convertToEntity(Product domain) {
        ProductEntity entity = new ProductEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setOnSale(domain.isOnSale());
        entity.setSalePrice(domain.getSalePrice());
        entity.setStock(domain.getStock());
        entity.setImageUrl(domain.getImageUrl());
        entity.setCategory(domain.getCategory());
        entity.setTags(domain.getTags());
        entity.setRating(domain.getRating());
        entity.setFeatured(domain.getFeatured());
        return entity;
    }
    
    private Product convertToDomain(ProductEntity entity) {
        Product domain = new Product();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setPrice(entity.getPrice());
        domain.setOnSale(entity.isOnSale());
        domain.setSalePrice(entity.getSalePrice());
        domain.setStock(entity.getStock());
        domain.setImageUrl(entity.getImageUrl());
        domain.setCategory(entity.getCategory());
        domain.setTags(entity.getTags());
        domain.setRating(entity.getRating());
        domain.setFeatured(entity.getFeatured());
        return domain;
    }
}
