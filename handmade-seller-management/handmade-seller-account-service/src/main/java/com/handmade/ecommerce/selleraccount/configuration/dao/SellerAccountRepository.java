package com.handmade.ecommerce.selleraccount.configuration.dao;

import com.handmade.ecommerce.seller.model.SellerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SellerAccountRepository extends JpaRepository<SellerAccount,String> {}
