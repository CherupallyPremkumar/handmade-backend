package com.handmade.ecommerce.payment.configuration.dao;

import com.handmade.ecommerce.payment.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for PaymentOrder entity
 * Handles database operations for individual payment orders
 */
@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, String> {

    /**
     * Find all payment orders for a payment
     */
    List<PaymentOrder> findByPaymentId(String paymentId);

    /**
     * Find all payment orders for a seller
     */
    List<PaymentOrder> findBySellerId(String sellerId);

    /**
     * Find payment order by PSP reference ID
     */
    Optional<PaymentOrder> findByPspReferenceId(String pspReferenceId);

    /**
     * Find payment orders by status
     */
    List<PaymentOrder> findByPaymentOrderStatus(String status);

    /**
     * Find payment orders by seller and status
     */
    List<PaymentOrder> findBySellerIdAndPaymentOrderStatus(String sellerId, String status);

    /**
     * Find payment orders where wallet not updated
     */
    List<PaymentOrder> findByWalletUpdatedFalse();

    /**
     * Find payment orders where ledger not updated
     */
    List<PaymentOrder> findByLedgerUpdatedFalse();
}
