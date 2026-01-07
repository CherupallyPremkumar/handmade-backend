package com.handmade.ecommerce.platform.marketplace.repository;

import com.handmade.ecommerce.platform.domain.entity.MarketplaceLocale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketplaceLocaleRepository extends JpaRepository<MarketplaceLocale, String> {
    
    /**
     * Find all locales for a marketplace
     */
    List<MarketplaceLocale> findByMarketplaceId(String marketplaceId);
    
    /**
     * Find active locales for a marketplace
     */
    List<MarketplaceLocale> findByMarketplaceIdAndActiveTrue(String marketplaceId);
    
    /**
     * Find default locale for a marketplace
     */
    Optional<MarketplaceLocale> findByMarketplaceIdAndIsDefaultTrue(String marketplaceId);
    
    /**
     * Find specific locale by marketplace and language
     */
    Optional<MarketplaceLocale> findByMarketplaceIdAndLanguage(String marketplaceId, String language);
}
