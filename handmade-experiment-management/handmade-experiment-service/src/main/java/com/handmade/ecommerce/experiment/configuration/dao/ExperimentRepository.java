package com.handmade.ecommerce.experiment.configuration.dao;

import com.handmade.ecommerce.experiment.model.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ExperimentRepository extends JpaRepository<Experiment,String> {}
