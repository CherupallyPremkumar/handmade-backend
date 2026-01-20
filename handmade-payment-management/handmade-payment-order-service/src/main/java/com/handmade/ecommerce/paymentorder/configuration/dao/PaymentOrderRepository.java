package com.handmade.ecommerce.paymentorder.configuration.dao;

import com.handmade.ecommerce.payment.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,String> {}
