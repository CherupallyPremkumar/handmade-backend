package com.handmade.ecommerce.customer.payment;

import com.handmade.ecommerce.customer.payment.entity.PaymentMethod;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PaymentMethod
 * Generated from entity file
 */
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {
    
    List<PaymentMethod> findByCustomerId(String customerId);
    List<PaymentMethod> findByMethodType(String methodType);
    List<PaymentMethod> findByProviderMethodId(String providerMethodId);
    List<PaymentMethod> findByCardholderName(String cardholderName);
    List<PaymentMethod> findByBillingAddressId(String billingAddressId);
    List<PaymentMethod> findByBankName(String bankName);
    List<PaymentMethod> findByBankAccountType(String bankAccountType);
    List<PaymentMethod> findByIsActive(Boolean isActive);
}
