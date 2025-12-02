package com.handmade.ecommerce.gstin.configuration.dao;

import com.handmade.ecommerce.gstin.model.Gstin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GstinRepository extends JpaRepository<Gstin, String> {
    
    /**
     * Find GSTIN by GSTIN number
     */
    Optional<Gstin> findByGstinNumber(String gstinNumber);
    
    /**
     * Check if GSTIN number already exists
     */
    boolean existsByGstinNumber(String gstinNumber);
    
    /**
     * Find all GSTINs for a specific seller
     */
    java.util.List<Gstin> findBySellerId(String sellerId);
    
    /**
     * Find GSTIN by seller ID and GSTIN number
     */
    Optional<Gstin> findBySellerIdAndGstinNumber(String sellerId, String gstinNumber);
    
    /**
     * Find all verified GSTINs for a seller
     */
    java.util.List<Gstin> findBySellerIdAndStatus(String sellerId, String status);
}
