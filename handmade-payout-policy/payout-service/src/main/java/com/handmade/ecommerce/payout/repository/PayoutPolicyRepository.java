package com.handmade.ecommerce.payout.repository;

import com.handmade.ecommerce.payout.domain.aggregate.PayoutPolicy;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for PayoutPolicy aggregate
 */
@Repository
public interface PayoutPolicyRepository extends JpaRepository<PayoutPolicy, String> {

    /**
     * Find active policy for country and seller type
     */
    @Query("""
            SELECT p FROM PayoutPolicy p
            WHERE p.countryCode = :countryCode
              AND p.sellerType = :sellerType
              AND p.stateId = 'ACTIVE'
              AND p.effectiveFrom <= :today
              AND (p.effectiveTo IS NULL OR p.effectiveTo >= :today)
            ORDER BY p.effectiveFrom DESC
            """)
    Optional<PayoutPolicy> findActivePolicy(
            @Param("countryCode") String countryCode,
            @Param("sellerType") SellerType sellerType,
            @Param("today") LocalDate today);

    /**
     * Find policy by version (for locked sellers)
     */
    Optional<PayoutPolicy> findByPolicyVersion(String policyVersion);

    /**
     * Find all active policies
     */
    @Query("SELECT p FROM PayoutPolicy p WHERE p.stateId = 'ACTIVE'")
    List<PayoutPolicy> findAllActive();

    /**
     * Find all draft policies
     */
    @Query("SELECT p FROM PayoutPolicy p WHERE p.stateId = 'DRAFT'")
    List<PayoutPolicy> findAllDrafts();

    /**
     * Find all deprecated policies
     */
    @Query("SELECT p FROM PayoutPolicy p WHERE p.stateId = 'DEPRECATED'")
    List<PayoutPolicy> findAllDeprecated();

    /**
     * Check if active policy exists
     */
    @Query("""
            SELECT COUNT(p) > 0 FROM PayoutPolicy p
            WHERE p.countryCode = :countryCode
              AND p.sellerType = :sellerType
              AND p.stateId = 'ACTIVE'
              AND p.effectiveFrom <= :today
              AND (p.effectiveTo IS NULL OR p.effectiveTo >= :today)
            """)
    boolean hasActivePolicy(
            @Param("countryCode") String countryCode,
            @Param("sellerType") SellerType sellerType,
            @Param("today") LocalDate today);
}
