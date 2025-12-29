package com.handmade.ecommerce.paymentorder.configuration.dao;

import com.handmade.ecommerce.paymentorder.model.Paymentorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PaymentorderRepository extends JpaRepository<Paymentorder,String> {}
