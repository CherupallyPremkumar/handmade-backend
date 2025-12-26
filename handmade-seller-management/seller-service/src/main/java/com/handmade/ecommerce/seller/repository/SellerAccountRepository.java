package com.handmade.ecommerce.seller.repository;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for SellerAccount aggregate
 */
@Repository
public interface SellerAccountRepository extends JpaRepository<SellerAccount, String> {
    
    /**
     * Find seller by email
     */
    Optional<SellerAccount> findByEmail(String email);
    
    /**
     * Find all sellers locked to a specific policy version
     * Useful for policy migration tracking
     */
    @Query("SELECT s FROM SellerAccount s WHERE s.onboardingPolicyVersion = :version")
    List<SellerAccount> findByPolicyVersion(@Param("version") String version);
    
    /**
     * Find all sellers locked to a specific policy ID
     */
    @Query("SELECT s FROM SellerAccount s WHERE s.onboardingPolicyId = :policyId")
    List<SellerAccount> findByPolicyId(@Param("policyId") String policyId);
    
    /**
     * Count sellers by policy version
     * Useful for reporting: "How many sellers are on 2024.1 policy?"
     */
    @Query("SELECT COUNT(s) FROM SellerAccount s WHERE s.onboardingPolicyVersion = :version")
    long countByPolicyVersion(@Param("version") String version);
}
