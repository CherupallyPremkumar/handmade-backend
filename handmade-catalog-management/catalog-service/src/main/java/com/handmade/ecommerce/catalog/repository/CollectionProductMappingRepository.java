package com.handmade.ecommerce.catalog.repository;

import com.handmade.ecommerce.catalog.model.CollectionProductMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionProductMappingRepository extends JpaRepository<CollectionProductMapping, Long> {

    void deleteByCollectionId(String collectionId);

    List<CollectionProductMapping> findByCollectionId(String collectionId);
}
