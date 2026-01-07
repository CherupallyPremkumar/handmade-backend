package com.handmade.ecommerce.policy.configuration.dao;

import com.handmade.ecommerce.policy.domain.aggregate.ListingPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ListingPolicyRepository extends JpaRepository<ListingPolicy, String> {
    
    Optional<ListingPolicy> findByPolicyVersion(String policyVersion);
    
    @Query("SELECT p FROM ListingPolicy p WHERE p.countryCode = ?1 AND " +
           "(p.category = ?2 OR p.category IS NULL) AND " +
           "p.currentState.stateId = 'ACTIVE' AND " +
           "p.effectiveDate <= ?3 ORDER BY p.category DESC")
    Optional<ListingPolicy> findActivePolicy(String country, String category, LocalDate date);
    
    @Query("SELECT p FROM ListingPolicy p WHERE p.currentState.stateId = 'ACTIVE' AND p.effectiveDate <= ?1")
    List<ListingPolicy> findAllActivePolicies(LocalDate date);
    
    @Query("SELECT p FROM ListingPolicy p WHERE p.currentState.stateId = 'DRAFT'")
    List<ListingPolicy> findAllDraftPolicies();
}
