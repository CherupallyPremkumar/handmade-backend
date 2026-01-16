package com.handmade.ecommerce.loyalty.configuration.dao;

import com.handmade.ecommerce.loyalty.model.Loyalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface LoyaltyRepository extends JpaRepository<Loyalty,String> {}
