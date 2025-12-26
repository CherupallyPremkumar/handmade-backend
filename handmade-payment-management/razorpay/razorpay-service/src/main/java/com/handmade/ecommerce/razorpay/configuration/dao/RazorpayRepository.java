package com.handmade.ecommerce.razorpay.configuration.dao;

import com.handmade.ecommerce.razorpay.model.Razorpay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface RazorpayRepository extends JpaRepository<Razorpay,String> {}
