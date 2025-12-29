package com.handmade.ecommerce.catalog.search.repository;

import com.handmade.ecommerce.catalog.search.model.CatalogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogEntryRepository extends JpaRepository<CatalogEntry, Long> {
    
    Optional<CatalogEntry> findByProductId(String productId);
    
    /**
     * Full-text search with relevance ranking
     * Uses PostgreSQL's ts_rank for scoring
     */
    @Query(value = """
        SELECT * FROM catalog_entry
        WHERE search_vector @@ to_tsquery('english', :query)
        ORDER BY ts_rank(search_vector, to_tsquery('english', :query)) DESC,
                 popularity DESC
        LIMIT :limit OFFSET :offset
        """, nativeQuery = true)
    List<CatalogEntry> searchByQuery(
        @Param("query") String query,
        @Param("limit") int limit,
        @Param("offset") int offset
    );
    
    /**
     * Search with category filter
     */
    @Query(value = """
        SELECT * FROM catalog_entry
        WHERE search_vector @@ to_tsquery('english', :query)
          AND category_id = :categoryId
        ORDER BY ts_rank(search_vector, to_tsquery('english', :query)) DESC
        LIMIT :limit OFFSET :offset
        """, nativeQuery = true)
    List<CatalogEntry> searchByCategoryAndQuery(
        @Param("categoryId") String categoryId,
        @Param("query") String query,
        @Param("limit") int limit,
        @Param("offset") int offset
    );
    
    /**
     * Get trending products (high popularity)
     */
    List<CatalogEntry> findTop20ByOrderByPopularityDesc();
}
