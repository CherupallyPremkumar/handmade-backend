package com.handmade.ecommerce.pricing.configuration.dao;

import com.handmade.ecommerce.pricing.model.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PricingRepository extends JpaRepository<Pricing,String> {}
