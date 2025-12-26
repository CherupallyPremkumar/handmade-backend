package com.handmade.ecommerce.orchestrator.configuration.dao;

import com.handmade.ecommerce.orchestrator.model.Orchestrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface OrchestratorRepository extends JpaRepository<Orchestrator,String> {}
