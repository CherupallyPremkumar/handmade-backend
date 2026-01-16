package com.handmade.ecommerce.risk.configuration.dao;

import com.handmade.ecommerce.risk.model.Risk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface RiskRepository extends JpaRepository<Risk,String> {}
