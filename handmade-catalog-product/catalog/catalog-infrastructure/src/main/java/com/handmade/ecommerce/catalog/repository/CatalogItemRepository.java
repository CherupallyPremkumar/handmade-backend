package com.handmade.ecommerce.catalog;

import com.handmade.ecommerce.catalog.entity.CatalogItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CatalogItem
 * Generated from entity file
 */
@Repository
public interface CatalogItemRepository extends JpaRepository<CatalogItem, String> {
    
    Optional<CatalogItem> findByProductId(String productId);
    List<CatalogItem> findByActive(Boolean active);

    // Existence checks
    boolean existsByProductId(String productId);
}
