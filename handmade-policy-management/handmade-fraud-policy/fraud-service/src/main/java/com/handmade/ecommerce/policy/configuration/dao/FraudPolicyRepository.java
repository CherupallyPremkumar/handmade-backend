package com.handmade.ecommerce.policy.configuration.dao;

import com.handmade.ecommerce.policy.domain.aggregate.FraudPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FraudPolicyRepository extends JpaRepository<FraudPolicy, String> {
    Optional<FraudPolicy> findByPolicyVersion(String policyVersion);
}
