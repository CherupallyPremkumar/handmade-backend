package com.handmade.ecommerce.paymentauthorization.configuration.dao;

import com.handmade.ecommerce.payment.model.PaymentAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PaymentAuthorizationRepository extends JpaRepository<PaymentAuthorization,String> {}
