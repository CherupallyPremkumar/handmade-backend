package com.handmade.ecommerce.content.localization;

import com.handmade.ecommerce.content.localization.entity.Region;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Region
 * Generated from entity file
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, String> {
    
    List<Region> findByRegionCode(String regionCode);
    List<Region> findByName(String name);
    List<Region> findByDefaultCurrencyCode(String defaultCurrencyCode);
    List<Region> findByIsActive(Boolean isActive);
}
