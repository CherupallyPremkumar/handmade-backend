package com.homebase.ecom.repository;


import com.homebase.ecom.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    
    Optional<PaymentEntity> findByTransactionId(String transactionId);
    
    Optional<PaymentEntity> findByMerchantTransactionId(String merchantTransactionId);
    
    List<PaymentEntity> findByOrderId(String orderId);
}
