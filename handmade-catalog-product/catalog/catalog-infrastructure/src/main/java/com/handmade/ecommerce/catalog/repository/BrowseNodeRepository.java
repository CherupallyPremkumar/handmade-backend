package com.handmade.ecommerce.catalog;

import com.handmade.ecommerce.catalog.entity.BrowseNode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for BrowseNode
 * Generated from entity file
 */
@Repository
public interface BrowseNodeRepository extends JpaRepository<BrowseNode, String> {
    
    List<BrowseNode> findByName(String name);
    Optional<BrowseNode> findBySlug(String slug);
    List<BrowseNode> findByParentId(String parentId);
    List<BrowseNode> findByActive(Boolean active);

    // Existence checks
    boolean existsBySlug(String slug);
}
