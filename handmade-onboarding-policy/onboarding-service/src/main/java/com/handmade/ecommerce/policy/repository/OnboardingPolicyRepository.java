package com.handmade.ecommerce.policy.repository;

import com.handmade.ecommerce.seller.domain.enums.SellerType;
import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for OnboardingPolicy aggregate
 */
@Repository
public interface OnboardingPolicyRepository extends JpaRepository<OnboardingPolicy, String> {
    
    /**
     * Find active policy for country and seller type
     * Active = stateId is ACTIVE AND effective date is valid
     */
    @Query("""
        SELECT p FROM OnboardingPolicy p
        WHERE p.countryCode = :countryCode
          AND p.sellerType = :sellerType
          AND p.stateId = 'ACTIVE'
          AND p.effectiveDate <= :today
        ORDER BY p.effectiveDate DESC
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
     * Find policy by version (for locked sellers)
     */
    Optional<OnboardingPolicy> findByVersion(String version);
    
    /**
     * Find all active policies
     */
    @Query("SELECT p FROM OnboardingPolicy p WHERE p.stateId = 'ACTIVE'")
    List<OnboardingPolicy> findAllActive();
    
    /**
     * Find all draft policies
     */
    @Query("SELECT p FROM OnboardingPolicy p WHERE p.stateId = 'DRAFT'")
    List<OnboardingPolicy> findAllDrafts();
    
    /**
     * Find all deprecated policies
     */
    @Query("SELECT p FROM OnboardingPolicy p WHERE p.stateId = 'DEPRECATED'")
    List<OnboardingPolicy> findAllDeprecated();
    
    /**
     * Check if active policy exists for country and seller type
     */
    @Query("""
        SELECT COUNT(p) > 0 FROM OnboardingPolicy p
        WHERE p.countryCode = :countryCode
          AND p.sellerType = :sellerType
          AND p.stateId = 'ACTIVE'
          AND p.effectiveDate <= :today
        """)
    boolean hasActivePolicy(
        @Param("countryCode") String countryCode,
        @Param("sellerType") SellerType sellerType,
        @Param("today") LocalDate today
    );

    /**
     * Find all active policies effective as of today
     */
    @Query("""
        SELECT p FROM OnboardingPolicy p
        WHERE p.stateId = 'ACTIVE'
          AND p.effectiveDate <= :today
        ORDER BY p.effectiveDate DESC
        """)
    List<OnboardingPolicy> findAllActivePolicies(@Param("today") LocalDate today);

    /**
     * Find all draft policies
     */
    @Query("SELECT p FROM OnboardingPolicy p WHERE p.stateId = 'DRAFT'")
    List<OnboardingPolicy> findAllDraftPolicies();
}
