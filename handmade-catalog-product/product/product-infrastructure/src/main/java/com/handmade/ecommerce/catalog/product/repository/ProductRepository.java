package com.handmade.ecommerce.catalog.product;

import com.handmade.ecommerce.catalog.product.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for Product
 * Generated from entity file
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
    List<Product> findBySellerId(String sellerId);
    List<Product> findByName(String name);
    List<Product> findByClassificationId(String classificationId);
    List<Product> findByParentId(String parentId);
    List<Product> findByVariationThemeId(String variationThemeId);
    List<Product> findByStatus(String status);

    // STM State queries
    @Query("SELECT e FROM Product e WHERE e.state.stateId = :stateId")
    List<Product> findByStateId(@Param("stateId") String stateId);

    @Query("SELECT e FROM Product e WHERE e.state.flowId = :flowId")
    List<Product> findByFlowId(@Param("flowId") String flowId);

    @Query("SELECT e FROM Product e WHERE e.state.flowId = :flowId AND e.state.stateId = :stateId")
    List<Product> findByFlowIdAndStateId(@Param("flowId") String flowId, @Param("stateId") String stateId);
}
