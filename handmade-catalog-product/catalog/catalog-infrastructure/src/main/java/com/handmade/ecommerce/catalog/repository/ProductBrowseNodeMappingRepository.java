package com.handmade.ecommerce.catalog;

import com.handmade.ecommerce.catalog.entity.ProductBrowseNodeMapping;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductBrowseNodeMapping
 * Generated from entity file
 */
@Repository
public interface ProductBrowseNodeMappingRepository extends JpaRepository<ProductBrowseNodeMapping, String> {
    
    List<ProductBrowseNodeMapping> findByProductId(String productId);
    List<ProductBrowseNodeMapping> findByBrowseNodeId(String browseNodeId);
}
