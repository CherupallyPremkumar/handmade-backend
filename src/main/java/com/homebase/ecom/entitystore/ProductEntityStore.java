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

    }

    @Override
    public Product retrieve(String id) {
        return null;

    }
    

}
