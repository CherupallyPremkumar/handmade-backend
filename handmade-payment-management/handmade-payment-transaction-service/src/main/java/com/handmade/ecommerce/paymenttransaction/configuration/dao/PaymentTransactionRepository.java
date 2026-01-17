package com.handmade.ecommerce.paymenttransaction.configuration.dao;

import com.handmade.ecommerce.payment.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction,String> {}
