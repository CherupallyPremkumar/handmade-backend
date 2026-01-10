package com.handmade.ecommerce.analytics.index;

import com.handmade.ecommerce.analytics.index.entity.SearchIndexSnapshot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SearchIndexSnapshot
 * Generated from entity file
 */
@Repository
public interface SearchIndexSnapshotRepository extends JpaRepository<SearchIndexSnapshot, String> {
    
    List<SearchIndexSnapshot> findByIndexName(String indexName);
    List<SearchIndexSnapshot> findByStatus(String status);
}
