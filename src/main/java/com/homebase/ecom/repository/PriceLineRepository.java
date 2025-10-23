package com.homebase.ecom.repository;

import com.homebase.ecom.domain.PriceLine;
import com.homebase.ecom.entity.PriceLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceLineRepository extends JpaRepository<PriceLineEntity,String> {
    Optional<PriceLineEntity> findByPriceIdAndCurrency(String priceId, String country);
}
