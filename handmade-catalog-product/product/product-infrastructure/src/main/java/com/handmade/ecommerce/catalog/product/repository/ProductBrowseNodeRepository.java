package com.handmade.ecommerce.catalog.product;

import com.handmade.ecommerce.catalog.product.entity.ProductBrowseNode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductBrowseNode
 * Generated from entity file
 */
@Repository
public interface ProductBrowseNodeRepository extends JpaRepository<ProductBrowseNode, String> {
    
    List<ProductBrowseNode> findByProductId(String productId);
    List<ProductBrowseNode> findByBrowseNodeId(String browseNodeId);
}
