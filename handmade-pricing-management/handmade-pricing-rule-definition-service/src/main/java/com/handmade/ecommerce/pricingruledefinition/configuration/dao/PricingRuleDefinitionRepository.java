package com.handmade.ecommerce.pricingruledefinition.configuration.dao;

import com.handmade.ecommerce.pricing.model.PricingRuleDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PricingRuleDefinitionRepository extends JpaRepository<PricingRuleDefinition,String> {}
