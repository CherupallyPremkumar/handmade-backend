package com.handmade.ecommerce.policydefinition.configuration.dao;

import com.handmade.ecommerce.policy.model.PolicyDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PolicyDefinitionRepository extends JpaRepository<PolicyDefinition,String> {}
