package com.handmade.ecommerce.customer.payment;

import com.handmade.ecommerce.customer.payment.entity.PaymentTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PaymentTransaction
 * Generated from entity file
 */
@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, String> {
    
    List<PaymentTransaction> findByOrderId(String orderId);
    List<PaymentTransaction> findByCustomerId(String customerId);
    List<PaymentTransaction> findByPaymentMethodId(String paymentMethodId);
    List<PaymentTransaction> findByTransactionType(String transactionType);
    List<PaymentTransaction> findByStatus(String status);
    List<PaymentTransaction> findByCurrencyCode(String currencyCode);
    List<PaymentTransaction> findByProviderTransactionId(String providerTransactionId);
    List<PaymentTransaction> findByAuthorizationCode(String authorizationCode);
    List<PaymentTransaction> findByBillingAddressId(String billingAddressId);
    List<PaymentTransaction> findByDisputeId(String disputeId);
}
