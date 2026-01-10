package com.handmade.ecommerce.customer.payment;

import com.handmade.ecommerce.customer.payment.entity.PaymentCapture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PaymentCapture
 * Generated from entity file
 */
@Repository
public interface PaymentCaptureRepository extends JpaRepository<PaymentCapture, String> {
    
    List<PaymentCapture> findByPaymentAuthorizationId(String paymentAuthorizationId);
    List<PaymentCapture> findByCurrencyCode(String currencyCode);
    List<PaymentCapture> findByProviderCaptureId(String providerCaptureId);
    List<PaymentCapture> findByStatus(String status);
    List<PaymentCapture> findBySettlementBatchId(String settlementBatchId);
}
