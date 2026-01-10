package com.handmade.ecommerce.customer.payment;

import com.handmade.ecommerce.customer.payment.entity.PaymentRefundTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PaymentRefundTransaction
 * Generated from entity file
 */
@Repository
public interface PaymentRefundTransactionRepository extends JpaRepository<PaymentRefundTransaction, String> {
    
    List<PaymentRefundTransaction> findByPaymentTransactionId(String paymentTransactionId);
    List<PaymentRefundTransaction> findByRefundId(String refundId);
    List<PaymentRefundTransaction> findByCurrencyCode(String currencyCode);
    List<PaymentRefundTransaction> findByProviderRefundId(String providerRefundId);
    List<PaymentRefundTransaction> findByStatus(String status);
}
