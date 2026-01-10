package com.handmade.ecommerce.analytics.search;

import com.handmade.ecommerce.analytics.search.entity.SearchResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SearchResult
 * Generated from entity file
 */
@Repository
public interface SearchResultRepository extends JpaRepository<SearchResult, String> {
    
    List<SearchResult> findBySearchQueryId(String searchQueryId);
    List<SearchResult> findByProductId(String productId);
}
