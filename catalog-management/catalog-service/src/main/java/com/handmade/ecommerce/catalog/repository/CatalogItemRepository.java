package com.handmade.ecommerce.catalog.repository;

import com.handmade.ecommerce.catalog.model.CatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogItemRepository extends JpaRepository<CatalogItem, String> {

    Optional<CatalogItem> findByProductId(String productId);
}
