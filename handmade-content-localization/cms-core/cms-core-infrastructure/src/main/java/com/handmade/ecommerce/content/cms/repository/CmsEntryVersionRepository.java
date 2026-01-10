package com.handmade.ecommerce.content.cms;

import com.handmade.ecommerce.content.cms.entity.CmsEntryVersion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CmsEntryVersion
 * Generated from entity file
 */
@Repository
public interface CmsEntryVersionRepository extends JpaRepository<CmsEntryVersion, String> {
    
    List<CmsEntryVersion> findByEntryId(String entryId);
    List<CmsEntryVersion> findByVersionNumber(String versionNumber);
}
