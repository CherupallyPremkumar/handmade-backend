package com.handmade.ecommerce.catalog;

import com.handmade.ecommerce.catalog.entity.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Collection
 * Generated from entity file
 */
@Repository
public interface CollectionRepository extends JpaRepository<Collection, String> {
    
    List<Collection> findByName(String name);
    Optional<Collection> findBySlug(String slug);
    List<Collection> findByType(String type);
    List<Collection> findByActive(Boolean active);

    // Existence checks
    boolean existsBySlug(String slug);
}
