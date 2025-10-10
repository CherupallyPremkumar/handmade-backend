package com.homebase.admin.repository;

import com.homebase.admin.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByTransactionId(String transactionId);
    
    Optional<Payment> findByMerchantTransactionId(String merchantTransactionId);
    
    List<Payment> findByOrderId(Long orderId);
    
    List<Payment> findByTenantIdAndStatus(String tenantId, Payment.PaymentStatus status);
}
