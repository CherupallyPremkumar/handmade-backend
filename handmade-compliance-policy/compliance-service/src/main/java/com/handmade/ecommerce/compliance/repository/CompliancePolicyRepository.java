package com.handmade.ecommerce.compliance.repository;

import com.handmade.ecommerce.compliance.domain.aggregate.CompliancePolicy;
import com.handmade.ecommerce.seller.domain.enums.SellerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for CompliancePolicy aggregate
 */
@Repository
public interface CompliancePolicyRepository extends JpaRepository<CompliancePolicy, String> {
    
    /**
     * Find active policy for country and seller type
     */
    @Query("""
        SELECT p FROM CompliancePolicy p
        WHERE p.countryCode = :countryCode
          AND p.sellerType = :sellerType
          AND p.stateId = 'ACTIVE'
          AND p.effectiveFrom <= :today
          AND (p.effectiveTo IS NULL OR p.effectiveTo >= :today)
        ORDER BY p.effectiveFrom DESC
        """)
    Optional<CompliancePolicy> findActivePolicy(
        @Param("countryCode") String countryCode,
        @Param("sellerType") SellerType sellerType,
        @Param("today") LocalDate today
    );
    
    /**
     * Find policy by version
     */
    Optional<CompliancePolicy> findByVersion(String version);
    
    /**
     * Find all active policies
     */
    @Query("SELECT p FROM CompliancePolicy p WHERE p.stateId = 'ACTIVE'")
    List<CompliancePolicy> findAllActive();
    
    /**
     * Find all draft policies
     */
    @Query("SELECT p FROM CompliancePolicy p WHERE p.stateId = 'DRAFT'")
    List<CompliancePolicy> findAllDrafts();
    
    /**
     * Find all deprecated policies
     */
    @Query("SELECT p FROM CompliancePolicy p WHERE p.stateId = 'DEPRECATED'")
    List<CompliancePolicy> findAllDeprecated();
}
