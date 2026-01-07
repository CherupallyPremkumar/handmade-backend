package com.handmade.ecommerce.policy.configuration.dao;

import com.handmade.ecommerce.policy.domain.aggregate.PerformancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerformancePolicyRepository extends JpaRepository<PerformancePolicy, String> {
    Optional<PerformancePolicy> findByPolicyVersion(String policyVersion);
}
