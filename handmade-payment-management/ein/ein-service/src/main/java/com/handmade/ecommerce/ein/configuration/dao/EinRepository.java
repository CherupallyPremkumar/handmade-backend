package com.handmade.ecommerce.ein.configuration.dao;

import com.handmade.ecommerce.ein.model.Ein;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EinRepository extends JpaRepository<Ein, String> {
    
    /**
     * Find EIN by EIN number
     */
    Optional<Ein> findByEinNumber(String einNumber);
    
    /**
     * Check if EIN number already exists
     */
    boolean existsByEinNumber(String einNumber);
    
    /**
     * Find all EINs for a specific seller
     */
    java.util.List<Ein> findBySellerId(String sellerId);
    
    /**
     * Find EIN by seller ID and EIN number
     */
    Optional<Ein> findBySellerIdAndEinNumber(String sellerId, String einNumber);
    
    /**
     * Find all verified EINs for a seller
     */
    java.util.List<Ein> findBySellerIdAndStatus(String sellerId, String status);
    
    /**
     * Find all EINs by campus code
     */
    java.util.List<Ein> findByCampusCode(String campusCode);
}
