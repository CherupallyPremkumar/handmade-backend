package com.handmade.ecommerce.catalog.product;

import com.handmade.ecommerce.catalog.product.entity.ProductAttributeValue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductAttributeValue
 * Generated from entity file
 */
@Repository
public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, String> {
    
    List<ProductAttributeValue> findByProductId(String productId);
    List<ProductAttributeValue> findByAttributeId(String attributeId);
    List<ProductAttributeValue> findByValueNumber(BigDecimal valueNumber);
}
