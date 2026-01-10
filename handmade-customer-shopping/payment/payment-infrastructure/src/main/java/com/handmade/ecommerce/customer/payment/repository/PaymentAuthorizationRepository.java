package com.handmade.ecommerce.customer.payment;

import com.handmade.ecommerce.customer.payment.entity.PaymentAuthorization;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PaymentAuthorization
 * Generated from entity file
 */
@Repository
public interface PaymentAuthorizationRepository extends JpaRepository<PaymentAuthorization, String> {
    
    List<PaymentAuthorization> findByPaymentTransactionId(String paymentTransactionId);
    List<PaymentAuthorization> findByAuthorizationCode(String authorizationCode);
    List<PaymentAuthorization> findByCurrencyCode(String currencyCode);
    List<PaymentAuthorization> findByProviderAuthorizationId(String providerAuthorizationId);
    List<PaymentAuthorization> findByStatus(String status);
}
