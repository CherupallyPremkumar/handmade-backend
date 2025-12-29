package com.handmade.ecommerce.ledger.configuration.dao;

import com.handmade.ecommerce.ledger.model.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerDao extends JpaRepository<Ledger, String> {

    List<Ledger> findByReferenceId(String referenceId);

    List<Ledger> findByStatus(String status);
}
