package com.handmade.ecommerce.policy.configuration.dao;

import com.handmade.ecommerce.policy.domain.aggregate.ReturnPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReturnPolicyRepository extends JpaRepository<ReturnPolicy, String> {
    
    Optional<ReturnPolicy> findByPolicyVersion(String policyVersion);
    
    @Query("SELECT p FROM ReturnPolicy p WHERE p.countryCode = ?1 AND " +
           "(p.category = ?2 OR p.category IS NULL) AND " +
           "p.currentState.stateId = 'ACTIVE' AND " +
           "p.effectiveDate <= ?3 ORDER BY p.category DESC")
    Optional<ReturnPolicy> findActivePolicy(String country, String category, LocalDate date);
}
