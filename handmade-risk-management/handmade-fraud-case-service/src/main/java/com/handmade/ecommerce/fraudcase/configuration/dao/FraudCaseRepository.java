package com.handmade.ecommerce.fraudcase.configuration.dao;

import com.handmade.ecommerce.risk.model.FraudCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface FraudCaseRepository extends JpaRepository<FraudCase,String> {}
