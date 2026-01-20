package com.handmade.ecommerce.paymentrefundtransaction.configuration.dao;

import com.handmade.ecommerce.payment.model.PaymentRefundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PaymentRefundTransactionRepository extends JpaRepository<PaymentRefundTransaction,String> {}
