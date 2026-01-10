package com.handmade.ecommerce.analytics.search;

import com.handmade.ecommerce.analytics.search.entity.SearchRedirect;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SearchRedirect
 * Generated from entity file
 */
@Repository
public interface SearchRedirectRepository extends JpaRepository<SearchRedirect, String> {
    
    List<SearchRedirect> findByRedirectType(String redirectType);
    List<SearchRedirect> findByPriority(String priority);
    List<SearchRedirect> findByIsActive(Boolean isActive);
}
