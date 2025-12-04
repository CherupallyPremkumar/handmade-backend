package com.handmade.ecommerce.paymentorder;

import com.handmade.ecommerce.paymentorder.model.PaymentOrder;

import java.util.List;

public interface PaymentOrderService {
    void createBatch(List<PaymentOrder> orders);

    /**
     * Marks all PaymentOrders for a payment as ready for distribution
     * 
     * @param paymentId The payment ID
     */
    void markReadyForDistribution(String paymentId);

    List<PaymentOrder> findByPaymentId(java.lang.String paymentId);
}
