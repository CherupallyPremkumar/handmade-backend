package com.handmade.ecommerce.seller.domain;

import com.handmade.ecommerce.seller.domain.entity.PaymentMethodd;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PaymentMethodd
 * Generated from entity file
 */
@Repository
public interface PaymentMethoddRepository extends JpaRepository<PaymentMethodd, String> {
    
    List<PaymentMethodd> findByExternalMethodId(String externalMethodId);
    List<PaymentMethodd> findByAccountHolderName(String accountHolderName);
    List<PaymentMethodd> findByBankName(String bankName);
    List<PaymentMethodd> findByAccountNumber(String accountNumber);
    List<PaymentMethodd> findByIfscCode(String ifscCode);
    List<PaymentMethodd> findBySwiftCode(String swiftCode);
    List<PaymentMethodd> findByPaypalEmail(String paypalEmail);
    List<PaymentMethodd> findByStripeAccountId(String stripeAccountId);
    List<PaymentMethodd> findByUpiId(String upiId);
}
