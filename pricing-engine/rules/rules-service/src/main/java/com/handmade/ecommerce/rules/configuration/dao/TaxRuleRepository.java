package com.handmade.ecommerce.rules.configuration.dao;

import com.handmade.ecommerce.rules.entity.TaxRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for TaxRule
 * Query methods for fetching tax rules by region
 */
@Repository
public interface TaxRuleRepository extends JpaRepository<TaxRule, Long> {

    /**
     * Find active tax rule for a region (country level)
     */
    @Query("SELECT r FROM TaxRule r WHERE r.isActive = true " +
           "AND r.regionCode = :regionCode " +
           "AND r.stateCode IS NULL " +
           "AND r.productCategory IS NULL " +
           "AND (r.validFrom IS NULL OR r.validFrom <= :now) " +
           "AND (r.validUntil IS NULL OR r.validUntil >= :now) " +
           "ORDER BY r.priority ASC")
    Optional<TaxRule> findByRegionCode(
        @Param("regionCode") String regionCode, 
        @Param("now") LocalDateTime now);

    /**
     * Find active tax rule for a region + state
     */
    @Query("SELECT r FROM TaxRule r WHERE r.isActive = true " +
           "AND r.regionCode = :regionCode " +
           "AND r.stateCode = :stateCode " +
           "AND r.productCategory IS NULL " +
           "AND (r.validFrom IS NULL OR r.validFrom <= :now) " +
           "AND (r.validUntil IS NULL OR r.validUntil >= :now) " +
           "ORDER BY r.priority ASC")
    Optional<TaxRule> findByRegionAndState(
        @Param("regionCode") String regionCode, 
        @Param("stateCode") String stateCode,
        @Param("now") LocalDateTime now);

    /**
     * Find all tax rules for a region
     */
    List<TaxRule> findByRegionCodeAndIsActiveTrueOrderByPriorityAsc(String regionCode);

    /**
     * Check if tax rules exist for a region
     */
    boolean existsByRegionCodeAndIsActiveTrue(String regionCode);
}
