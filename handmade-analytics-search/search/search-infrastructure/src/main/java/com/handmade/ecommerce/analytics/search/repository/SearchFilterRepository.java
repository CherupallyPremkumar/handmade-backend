package com.handmade.ecommerce.analytics.search;

import com.handmade.ecommerce.analytics.search.entity.SearchFilter;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SearchFilter
 * Generated from entity file
 */
@Repository
public interface SearchFilterRepository extends JpaRepository<SearchFilter, String> {
    
    List<SearchFilter> findBySearchQueryId(String searchQueryId);
    List<SearchFilter> findByFilterType(String filterType);
    List<SearchFilter> findByFilterKey(String filterKey);
}
