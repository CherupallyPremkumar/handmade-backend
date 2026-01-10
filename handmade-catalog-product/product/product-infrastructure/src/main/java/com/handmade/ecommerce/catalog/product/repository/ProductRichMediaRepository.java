package com.handmade.ecommerce.catalog.product;

import com.handmade.ecommerce.catalog.product.entity.ProductRichMedia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductRichMedia
 * Generated from entity file
 */
@Repository
public interface ProductRichMediaRepository extends JpaRepository<ProductRichMedia, String> {
    
    List<ProductRichMedia> findByProductId(String productId);
    List<ProductRichMedia> findByMediaType(String mediaType);
}
