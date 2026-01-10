package com.handmade.ecommerce.platform.domain;

import com.handmade.ecommerce.platform.domain.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ExchangeRate
 * Generated from entity file
 */
@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, String> {
    
    // No auto-generated query methods
}
