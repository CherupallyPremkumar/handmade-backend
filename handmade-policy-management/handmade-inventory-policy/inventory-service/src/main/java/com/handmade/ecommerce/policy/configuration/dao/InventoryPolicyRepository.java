package com.handmade.ecommerce.policy.configuration.dao;

import com.handmade.ecommerce.policy.domain.aggregate.InventoryPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface InventoryPolicyRepository extends JpaRepository<InventoryPolicy, String> {
    
    Optional<InventoryPolicy> findByPolicyVersion(String policyVersion);
    
    @Query("SELECT p FROM InventoryPolicy p WHERE p.countryCode = ?1 AND " +
           "(p.category = ?2 OR p.category IS NULL) AND " +
           "p.currentState.stateId = 'ACTIVE' AND " +
           "p.effectiveDate <= ?3 ORDER BY p.category DESC")
    Optional<InventoryPolicy> findActivePolicy(String country, String category, LocalDate date);
}
