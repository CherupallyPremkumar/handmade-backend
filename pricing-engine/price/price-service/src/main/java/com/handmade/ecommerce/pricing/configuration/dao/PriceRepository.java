package com.handmade.ecommerce.pricing.configuration.dao;

import com.handmade.ecommerce.pricing.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, String> {

    /**
     * Find price by variant ID
     */
    Optional<Price> findByVariantId(String variantId);
}
