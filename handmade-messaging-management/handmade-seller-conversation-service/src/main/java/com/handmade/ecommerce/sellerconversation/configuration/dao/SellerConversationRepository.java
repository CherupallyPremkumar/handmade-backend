package com.handmade.ecommerce.sellerconversation.configuration.dao;

import com.handmade.ecommerce.messaging.model.SellerConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SellerConversationRepository extends JpaRepository<SellerConversation,String> {}
