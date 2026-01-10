package com.handmade.ecommerce.analytics.search;

import com.handmade.ecommerce.analytics.search.entity.SearchSynonym;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SearchSynonym
 * Generated from entity file
 */
@Repository
public interface SearchSynonymRepository extends JpaRepository<SearchSynonym, String> {
    
    List<SearchSynonym> findBySynonymType(String synonymType);
    List<SearchSynonym> findByIsActive(Boolean isActive);
}
