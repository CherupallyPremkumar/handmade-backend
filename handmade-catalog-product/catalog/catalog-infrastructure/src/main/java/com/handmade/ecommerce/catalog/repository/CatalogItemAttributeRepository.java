package com.handmade.ecommerce.catalog;

import com.handmade.ecommerce.catalog.entity.CatalogItemAttribute;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CatalogItemAttribute
 * Generated from entity file
 */
@Repository
public interface CatalogItemAttributeRepository extends JpaRepository<CatalogItemAttribute, String> {
    
    List<CatalogItemAttribute> findByCatalogItemId(String catalogItemId);
    List<CatalogItemAttribute> findByAttributeId(String attributeId);
    List<CatalogItemAttribute> findByValueNumber(BigDecimal valueNumber);
}
