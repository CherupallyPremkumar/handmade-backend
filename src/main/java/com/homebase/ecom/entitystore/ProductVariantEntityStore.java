package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.ProductVariant;
import com.homebase.ecom.repository.ProductVariantRepository;
import org.chenile.utils.entity.service.EntityStore;

public class ProductVariantEntityStore implements EntityStore<ProductVariant> {

    private final ProductVariantRepository productVariantRepository;

    public ProductVariantEntityStore(ProductVariantRepository productVariantRepository) {
        this.productVariantRepository = productVariantRepository;
    }

    @Override
    public void store(ProductVariant entity) {

    }

    @Override
    public ProductVariant retrieve(String id) {
        return null;
    }
}
