package com.handmade.ecommerce.rules.configuration.dao;

import com.handmade.ecommerce.rules.entity.DiscountRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for DiscountRule
 * Query methods for fetching active discount rules
 */
@Repository
public interface DiscountRuleRepository extends JpaRepository<DiscountRule, Long> {

    /**
     * Find all active global discount rules
     */
    @Query("SELECT r FROM DiscountRule r WHERE r.isActive = true " +
           "AND r.scope = 'GLOBAL' " +
           "AND (r.validFrom IS NULL OR r.validFrom <= :now) " +
           "AND (r.validUntil IS NULL OR r.validUntil >= :now) " +
           "ORDER BY r.priority ASC")
    List<DiscountRule> findActiveGlobalRules(@Param("now") LocalDateTime now);

    /**
     * Find active rules for a specific category
     */
    @Query("SELECT r FROM DiscountRule r WHERE r.isActive = true " +
           "AND ((r.scope = 'GLOBAL') OR (r.scope = 'CATEGORY' AND r.scopeValue = :categoryId)) " +
           "AND (r.validFrom IS NULL OR r.validFrom <= :now) " +
           "AND (r.validUntil IS NULL OR r.validUntil >= :now) " +
           "ORDER BY r.priority ASC")
    List<DiscountRule> findActiveRulesForCategory(
        @Param("categoryId") String categoryId, 
        @Param("now") LocalDateTime now);

    /**
     * Find active rules for a specific seller
     */
    @Query("SELECT r FROM DiscountRule r WHERE r.isActive = true " +
           "AND ((r.scope = 'GLOBAL') OR (r.scope = 'SELLER' AND r.scopeValue = :sellerId)) " +
           "AND (r.validFrom IS NULL OR r.validFrom <= :now) " +
           "AND (r.validUntil IS NULL OR r.validUntil >= :now) " +
           "ORDER BY r.priority ASC")
    List<DiscountRule> findActiveRulesForSeller(
        @Param("sellerId") String sellerId, 
        @Param("now") LocalDateTime now);

    /**
     * Find rules by type
     */
    List<DiscountRule> findByRuleTypeAndIsActiveTrue(String ruleType);
}
