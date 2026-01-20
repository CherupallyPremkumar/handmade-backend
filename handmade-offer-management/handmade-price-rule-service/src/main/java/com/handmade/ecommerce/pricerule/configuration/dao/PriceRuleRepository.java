package com.handmade.ecommerce.pricerule.configuration.dao;

import com.handmade.ecommerce.offer.model.PriceRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PriceRuleRepository extends JpaRepository<PriceRule,String> {}
