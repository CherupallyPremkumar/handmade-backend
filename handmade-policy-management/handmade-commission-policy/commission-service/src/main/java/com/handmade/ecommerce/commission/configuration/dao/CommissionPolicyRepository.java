package com.handmade.ecommerce.commission.configuration.dao;

import com.handmade.ecommerce.commission.domain.aggregate.CommissionPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CommissionPolicyRepository extends JpaRepository<CommissionPolicy, String> {

    @Query("SELECT p FROM CommissionPolicy p WHERE p.countryCode = :countryCode AND p.category = :category " +
           "AND p.stateId = 'ACTIVE' AND :date >= p.effectiveDate " +
           "ORDER BY p.effectiveDate DESC LIMIT 1")
    Optional<CommissionPolicy> findActivePolicy(@Param("countryCode") String countryCode, 
                                              @Param("category") String category, 
                                              @Param("date") LocalDate date);

    Optional<CommissionPolicy> findByPolicyVersion(String policyVersion);
}
