package com.handmade.ecommerce.catalog.product;

import com.handmade.ecommerce.catalog.product.entity.ProductTags;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductTags
 * Generated from entity file
 */
@Repository
public interface ProductTagsRepository extends JpaRepository<ProductTags, String> {
    
    List<ProductTags> findByProductId(String productId);
    List<ProductTags> findByTagName(String tagName);
}
