package com.handmade.ecommerce.promotion.repository;

import com.handmade.ecommerce.promotion.domain.aggregate.PromotionPolicy;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for PromotionPolicy aggregate
 */
@Repository
public interface PromotionPolicyRepository extends JpaRepository<PromotionPolicy, String> {

  /**
   * Find active policy for country and seller type
   */
  @Query("""
      SELECT p FROM PromotionPolicy p
      WHERE p.countryCode = :countryCode
        AND p.sellerType = :sellerType
        AND p.stateId = 'ACTIVE'
        AND p.effectiveFrom <= :today
        AND (p.effectiveTo IS NULL OR p.effectiveTo >= :today)
      ORDER BY p.effectiveFrom DESC
      """)
  Optional<PromotionPolicy> findActivePolicy(
      @Param("countryCode") String countryCode,
      @Param("sellerType") SellerType sellerType,
      @Param("today") LocalDate today);

  /**
   * Find policy by version
   */
  Optional<PromotionPolicy> findByPolicyVersion(String policyVersion);

  /**
   * Find all active policies
   */
  @Query("SELECT p FROM PromotionPolicy p WHERE p.stateId = 'ACTIVE'")
  List<PromotionPolicy> findAllActive();

  /**
   * Find all active policies within date range
   */
  @Query("""
      SELECT p FROM PromotionPolicy p
      WHERE p.stateId = 'ACTIVE'
        AND p.effectiveFrom <= :today
        AND (p.effectiveTo IS NULL OR p.effectiveTo >= :today)
      """)
  List<PromotionPolicy> findAllActiveToday(@Param("today") LocalDate today);

  /**
   * Find all draft policies
   */
  @Query("SELECT p FROM PromotionPolicy p WHERE p.stateId = 'DRAFT'")
  List<PromotionPolicy> findAllDrafts();

  /**
   * Find all deprecated policies
   */
  @Query("SELECT p FROM PromotionPolicy p WHERE p.stateId = 'DEPRECATED'")
  List<PromotionPolicy> findAllDeprecated();
}
