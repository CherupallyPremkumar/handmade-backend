package com.handmade.ecommerce.content.cms;

import com.handmade.ecommerce.content.cms.entity.CmsEntry;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CmsEntry
 * Generated from entity file
 */
@Repository
public interface CmsEntryRepository extends JpaRepository<CmsEntry, String> {
    
    List<CmsEntry> findBySchemaId(String schemaId);
    Optional<CmsEntry> findBySlug(String slug);
    List<CmsEntry> findByStatus(String status);
    List<CmsEntry> findByActiveVersionId(String activeVersionId);

    // Existence checks
    boolean existsBySlug(String slug);
}
