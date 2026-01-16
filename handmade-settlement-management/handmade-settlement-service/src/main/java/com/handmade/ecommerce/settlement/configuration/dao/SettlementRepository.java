package com.handmade.ecommerce.settlement.configuration.dao;

import com.handmade.ecommerce.settlement.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SettlementRepository extends JpaRepository<Settlement,String> {}
