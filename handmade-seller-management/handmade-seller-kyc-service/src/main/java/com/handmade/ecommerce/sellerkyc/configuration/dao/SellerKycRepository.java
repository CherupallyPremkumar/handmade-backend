package com.handmade.ecommerce.sellerkyc.configuration.dao;

import com.handmade.ecommerce.seller.model.SellerKyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SellerKycRepository extends JpaRepository<SellerKyc,String> {}
