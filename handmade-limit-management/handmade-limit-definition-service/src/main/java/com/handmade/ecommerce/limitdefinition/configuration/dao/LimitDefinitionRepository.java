package com.handmade.ecommerce.limitdefinition.configuration.dao;

import com.handmade.ecommerce.limit.model.LimitDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface LimitDefinitionRepository extends JpaRepository<LimitDefinition,String> {}
