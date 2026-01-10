package com.handmade.ecommerce.order.dispute;

import com.handmade.ecommerce.order.dispute.entity.Dispute;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Dispute
 * Generated from entity file
 */
@Repository
public interface DisputeRepository extends JpaRepository<Dispute, String> {
    
    Optional<Dispute> findByDisputeNumber(String disputeNumber);
    List<Dispute> findByOrderId(String orderId);
    List<Dispute> findByPaymentTransactionId(String paymentTransactionId);
    List<Dispute> findByCustomerId(String customerId);
    List<Dispute> findBySellerId(String sellerId);
    List<Dispute> findByDisputeType(String disputeType);
    List<Dispute> findByCurrencyCode(String currencyCode);
    List<Dispute> findByStatus(String status);
    List<Dispute> findByProviderDisputeId(String providerDisputeId);
    List<Dispute> findByResolutionType(String resolutionType);

    // Existence checks
    boolean existsByDisputeNumber(String disputeNumber);
}
