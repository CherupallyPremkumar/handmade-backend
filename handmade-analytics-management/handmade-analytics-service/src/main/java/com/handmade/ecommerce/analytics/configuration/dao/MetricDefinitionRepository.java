package com.handmade.ecommerce.analytics.configuration.dao;

import com.handmade.ecommerce.analytics.model.MetricDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface MetricDefinitionRepository extends JpaRepository<MetricDefinition,String> {}
