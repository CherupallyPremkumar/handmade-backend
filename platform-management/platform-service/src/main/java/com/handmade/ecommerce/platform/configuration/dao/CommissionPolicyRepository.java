package com.handmade.ecommerce.platform.configuration.dao;

import com.handmade.ecommerce.platform.domain.aggregate.CommissionPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository for CommissionPolicy
 */
@Repository
public interface CommissionPolicyRepository extends JpaRepository<CommissionPolicy, String> {
    
    /**
     * Find the currently active policy
     */
    @Query("SELECT cp FROM CommissionPolicy cp WHERE cp.isActive = true " +
           "AND cp.effectiveFrom <= :now " +
           "AND (cp.effectiveTo IS NULL OR cp.effectiveTo >= :now) " +
           "ORDER BY cp.effectiveFrom DESC")
    Optional<CommissionPolicy> findActivePolicy(LocalDateTime now);
    
    /**
     * Find policy by name
     */
    Optional<CommissionPolicy> findByPolicyName(String policyName);
}
