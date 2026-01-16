package com.handmade.ecommerce.promotion.configuration.dao;

import com.handmade.ecommerce.promotion.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PromotionRepository extends JpaRepository<Promotion,String> {}
