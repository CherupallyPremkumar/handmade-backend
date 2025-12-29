package com.handmade.ecommerce.reconciliation.configuration.dao;

import com.handmade.ecommerce.reconciliation.model.Reconciliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ReconciliationRepository extends JpaRepository<Reconciliation,String> {}
