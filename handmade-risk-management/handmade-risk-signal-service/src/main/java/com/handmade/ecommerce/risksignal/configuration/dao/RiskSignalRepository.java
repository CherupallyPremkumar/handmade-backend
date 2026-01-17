package com.handmade.ecommerce.risksignal.configuration.dao;

import com.handmade.ecommerce.risk.model.RiskSignal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface RiskSignalRepository extends JpaRepository<RiskSignal,String> {}
