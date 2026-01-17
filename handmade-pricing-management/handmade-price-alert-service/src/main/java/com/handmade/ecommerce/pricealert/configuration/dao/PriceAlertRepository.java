package com.handmade.ecommerce.pricealert.configuration.dao;

import com.handmade.ecommerce.pricing.model.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PriceAlertRepository extends JpaRepository<PriceAlert,String> {}
