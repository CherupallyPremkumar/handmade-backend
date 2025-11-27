package com.handmade.ecommerce.price.configuration.dao;

import com.handmade.ecommerce.pricing.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, String> {
}
