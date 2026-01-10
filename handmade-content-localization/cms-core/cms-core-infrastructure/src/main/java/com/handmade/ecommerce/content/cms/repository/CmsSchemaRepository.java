package com.handmade.ecommerce.content.cms;

import com.handmade.ecommerce.content.cms.entity.CmsSchema;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CmsSchema
 * Generated from entity file
 */
@Repository
public interface CmsSchemaRepository extends JpaRepository<CmsSchema, String> {
    
    Optional<CmsSchema> findBySchemaCode(String schemaCode);
    List<CmsSchema> findByName(String name);

    // Existence checks
    boolean existsBySchemaCode(String schemaCode);
}
