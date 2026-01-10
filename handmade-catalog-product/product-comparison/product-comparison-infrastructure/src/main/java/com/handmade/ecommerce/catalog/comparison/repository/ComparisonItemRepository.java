package com.handmade.ecommerce.catalog.comparison;

import com.handmade.ecommerce.catalog.comparison.entity.ComparisonItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ComparisonItem
 * Generated from entity file
 */
@Repository
public interface ComparisonItemRepository extends JpaRepository<ComparisonItem, String> {
    
    List<ComparisonItem> findByComparisonId(String comparisonId);
    List<ComparisonItem> findByProductId(String productId);
}
