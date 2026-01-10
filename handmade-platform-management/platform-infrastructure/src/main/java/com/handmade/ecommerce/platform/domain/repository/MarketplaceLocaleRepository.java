package com.handmade.ecommerce.platform.domain;

import com.handmade.ecommerce.platform.domain.entity.MarketplaceLocale;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for MarketplaceLocale
 * Generated from entity file
 */
@Repository
public interface MarketplaceLocaleRepository extends JpaRepository<MarketplaceLocale, String> {
    
    List<MarketplaceLocale> findByMarketplaceId(String marketplaceId);
}
