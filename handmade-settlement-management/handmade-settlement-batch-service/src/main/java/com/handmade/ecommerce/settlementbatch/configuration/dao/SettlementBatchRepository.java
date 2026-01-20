package com.handmade.ecommerce.settlementbatch.configuration.dao;

import com.handmade.ecommerce.settlement.model.SettlementBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SettlementBatchRepository extends JpaRepository<SettlementBatch,String> {}
