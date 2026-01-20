package com.handmade.ecommerce.giftcard.configuration.dao;

import com.handmade.ecommerce.customer.model.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface GiftCardRepository extends JpaRepository<GiftCard,String> {}
