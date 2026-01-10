package com.handmade.ecommerce.catalog;

import com.handmade.ecommerce.catalog.entity.CollectionProductMapping;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CollectionProductMapping
 * Generated from entity file
 */
@Repository
public interface CollectionProductMappingRepository extends JpaRepository<CollectionProductMapping, String> {
    
    List<CollectionProductMapping> findByCollectionId(String collectionId);
    List<CollectionProductMapping> findByProductId(String productId);
}
