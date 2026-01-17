package com.handmade.ecommerce.paymentcapture.configuration.dao;

import com.handmade.ecommerce.payment.model.PaymentCapture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PaymentCaptureRepository extends JpaRepository<PaymentCapture,String> {}
