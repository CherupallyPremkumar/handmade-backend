package com.handmade.ecommerce.experiment.configuration.dao;

import com.handmade.ecommerce.experiment.model.ExperimentDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ExperimentDefinitionRepository extends JpaRepository<ExperimentDefinition,String> {}
