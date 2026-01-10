package com.handmade.ecommerce.content.cms;

import com.handmade.ecommerce.content.cms.entity.ContentAsset;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ContentAsset
 * Generated from entity file
 */
@Repository
public interface ContentAssetRepository extends JpaRepository<ContentAsset, String> {
    
    List<ContentAsset> findByAssetType(String assetType);
}
