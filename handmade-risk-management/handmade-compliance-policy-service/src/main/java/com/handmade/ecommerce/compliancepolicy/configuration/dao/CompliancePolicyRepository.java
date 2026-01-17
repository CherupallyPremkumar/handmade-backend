package com.handmade.ecommerce.compliancepolicy.configuration.dao;

import com.handmade.ecommerce.risk.model.CompliancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface CompliancePolicyRepository extends JpaRepository<CompliancePolicy,String> {}
