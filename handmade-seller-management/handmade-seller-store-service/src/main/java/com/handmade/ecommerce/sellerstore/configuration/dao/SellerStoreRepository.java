package com.handmade.ecommerce.sellerstore.configuration.dao;

import com.handmade.ecommerce.seller.model.SellerStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SellerStoreRepository extends JpaRepository<SellerStore,String> {}
