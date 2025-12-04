package com.handmade.ecommerce.ledger.configuration.dao;

import com.handmade.ecommerce.ledger.model.LedgerLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LedgerLineDao extends JpaRepository<LedgerLine, String> {

    List<LedgerLine> findByAccount(String account);

    @Query("SELECT SUM(CASE WHEN l.entryType = 'CREDIT' THEN l.amount ELSE -l.amount END) " +
            "FROM LedgerLine l WHERE l.account = :account")
    BigDecimal getAccountBalance(String account);
}
