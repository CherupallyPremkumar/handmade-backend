package com.handmade.ecommerce.platform.marketplace.repository;

import com.handmade.ecommerce.platform.domain.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, String> {
    
    /**
     * Find the latest exchange rate valid at a specific time
     */
    @Query("SELECT e FROM ExchangeRate e WHERE " +
           "e.baseCurrency = :baseCurrency AND " +
           "e.targetCurrency = :targetCurrency AND " +
           "e.effectiveFrom <= :asOf AND " +
           "(e.effectiveTo IS NULL OR e.effectiveTo >= :asOf) " +
           "ORDER BY e.effectiveFrom DESC")
    Optional<ExchangeRate> findLatestRate(
        @Param("baseCurrency") String baseCurrency,
        @Param("targetCurrency") String targetCurrency,
        @Param("asOf") LocalDateTime asOf
    );
    
    /**
     * Find current active rate
     */
    default Optional<ExchangeRate> findCurrentRate(String baseCurrency, String targetCurrency) {
        return findLatestRate(baseCurrency, targetCurrency, LocalDateTime.now());
    }
}
