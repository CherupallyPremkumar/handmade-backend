package com.handmade.ecommerce.financialtransaction.configuration.dao;

import com.handmade.ecommerce.finance.model.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction,String> {}
