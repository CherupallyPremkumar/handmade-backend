package com.handmade.ecommerce.platform.repository;

import com.handmade.ecommerce.platform.domain.enums.SellerType;
import com.handmade.ecommerce.platform.domain.policy.OnboardingPolicy;
import com.handmade.ecommerce.platform.domain.policy.PolicyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for OnboardingPolicy
 */
@Repository
public interface OnboardingPolicyRepository extends JpaRepository<OnboardingPolicy, String> {
    
    /**
     * Find the active policy for a specific country and seller type
     * 
     * Returns the most recent ACTIVE policy that is effective as of today
     * 
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param sellerType Seller type
     * @param today Current date
     * @return Active policy if exists
     */
    @Query("""
        SELECT p FROM OnboardingPolicy p
        WHERE p.countryCode = :countryCode
          AND p.sellerType = :sellerType
          AND p.status = 'ACTIVE'
          AND p.effectiveDate <= :today
        ORDER BY p.effectiveDate DESC
        LIMIT 1
        """)
    Optional<OnboardingPolicy> findActivePolicy(
        @Param("countryCode") String countryCode,
        @Param("sellerType") SellerType sellerType,
        @Param("today") LocalDate today
    );
    
    /**
     * Find all policies for a specific country and seller type
     */
    List<OnboardingPolicy> findByCountryCodeAndSellerTypeOrderByEffectiveDateDesc(
        String countryCode,
        SellerType sellerType
    );
    
    /**
     * Find policy by version
     */
    Optional<OnboardingPolicy> findByVersion(String version);
    
    /**
     * Find all active policies
     */
    List<OnboardingPolicy> findByStatus(PolicyStatus status);
    
    /**
     * Check if an active policy exists for country and seller type
     */
    @Query("""
        SELECT COUNT(p) > 0 FROM OnboardingPolicy p
        WHERE p.countryCode = :countryCode
          AND p.sellerType = :sellerType
          AND p.status = 'ACTIVE'
          AND p.effectiveDate <= :today
        """)
    boolean hasActivePolicy(
        @Param("countryCode") String countryCode,
        @Param("sellerType") SellerType sellerType,
        @Param("today") LocalDate today
    );
}
