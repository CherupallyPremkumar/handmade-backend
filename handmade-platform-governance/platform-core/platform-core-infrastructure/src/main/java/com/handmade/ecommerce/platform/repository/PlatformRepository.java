package com.handmade.ecommerce.platform;

import com.handmade.ecommerce.platform.entity.Platform;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Platform
 * Generated from entity file
 */
@Repository
public interface PlatformRepository extends JpaRepository<Platform, String> {
    
    Optional<Platform> findByPlatformCode(String platformCode);
    List<Platform> findByName(String name);
    List<Platform> findByCountryCode(String countryCode);
    List<Platform> findByCurrencyCode(String currencyCode);
    List<Platform> findByMarketplaceType(String marketplaceType);
    List<Platform> findByIsActive(Boolean isActive);

    // Existence checks
    boolean existsByPlatformCode(String platformCode);
}
