package com.handmade.ecommerce.platform.marketplace.repository;

import com.handmade.ecommerce.platform.domain.aggregate.Marketplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketplaceRepository extends JpaRepository<Marketplace, String> {
    
    /**
     * Find marketplace by marketplace ID
     */
    Optional<Marketplace> findByMarketplaceId(String marketplaceId);
    
    /**
     * Find marketplace by country code
     */
    Optional<Marketplace> findByCountryCode(String countryCode);
    
    /**
     * Find all active marketplaces
     */
    @Query("SELECT m FROM Marketplace m WHERE m.currentState.stateId = 'ACTIVE'")
    List<Marketplace> findAllActive();
    
    /**
     * Check if marketplace exists by ID
     */
    boolean existsByMarketplaceId(String marketplaceId);
    
    /**
     * Check if marketplace exists by country code
     */
    boolean existsByCountryCode(String countryCode);
}
