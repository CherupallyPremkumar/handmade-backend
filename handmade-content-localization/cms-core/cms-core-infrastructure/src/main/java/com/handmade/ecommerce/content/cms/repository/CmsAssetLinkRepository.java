package com.handmade.ecommerce.content.cms;

import com.handmade.ecommerce.content.cms.entity.CmsAssetLink;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CmsAssetLink
 * Generated from entity file
 */
@Repository
public interface CmsAssetLinkRepository extends JpaRepository<CmsAssetLink, String> {
    
    List<CmsAssetLink> findByEntryId(String entryId);
    List<CmsAssetLink> findByAssetId(String assetId);
    List<CmsAssetLink> findByFieldKey(String fieldKey);
}
