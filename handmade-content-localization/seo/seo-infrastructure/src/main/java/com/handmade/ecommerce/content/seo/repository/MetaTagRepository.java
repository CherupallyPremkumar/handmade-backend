package com.handmade.ecommerce.content.seo;

import com.handmade.ecommerce.content.seo.entity.MetaTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for MetaTag
 * Generated from entity file
 */
@Repository
public interface MetaTagRepository extends JpaRepository<MetaTag, String> {
    
    List<MetaTag> findByPageId(String pageId);
    List<MetaTag> findByEntityId(String entityId);
    List<MetaTag> findByEntityType(String entityType);
    List<MetaTag> findByMetaKey(String metaKey);
}
