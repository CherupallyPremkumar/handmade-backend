package com.handmade.ecommerce.analytics.search;

import com.handmade.ecommerce.analytics.search.entity.SearchQuery;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SearchQuery
 * Generated from entity file
 */
@Repository
public interface SearchQueryRepository extends JpaRepository<SearchQuery, String> {
    
    List<SearchQuery> findByCustomerId(String customerId);
    List<SearchQuery> findBySessionId(String sessionId);
    List<SearchQuery> findByBrowseNodeId(String browseNodeId);
    List<SearchQuery> findByClickedProductId(String clickedProductId);
    List<SearchQuery> findByOrderId(String orderId);
}
