package com.handmade.ecommerce.sellerverification.configuration.dao;

import com.handmade.ecommerce.onboarding.model.SellerVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SellerVerificationRepository extends JpaRepository<SellerVerification,String> {}
