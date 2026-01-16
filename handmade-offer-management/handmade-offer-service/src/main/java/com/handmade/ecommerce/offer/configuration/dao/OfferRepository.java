package com.handmade.ecommerce.offer.configuration.dao;

import com.handmade.ecommerce.offer.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface OfferRepository extends JpaRepository<Offer,String> {}
