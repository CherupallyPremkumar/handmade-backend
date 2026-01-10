package com.handmade.ecommerce.catalog.comparison;

import com.handmade.ecommerce.catalog.comparison.entity.ProductComparison;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductComparison
 * Generated from entity file
 */
@Repository
public interface ProductComparisonRepository extends JpaRepository<ProductComparison, String> {
    
    List<ProductComparison> findByCustomerId(String customerId);
    List<ProductComparison> findBySessionId(String sessionId);
    List<ProductComparison> findByComparisonName(String comparisonName);
    List<ProductComparison> findByCategoryId(String categoryId);
}
