package com.handmade.ecommerce.content.localization;

import com.handmade.ecommerce.content.localization.entity.Currency;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Currency
 * Generated from entity file
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
    
    List<Currency> findByCurrencyCode(String currencyCode);
    List<Currency> findByName(String name);
    List<Currency> findByIsActive(Boolean isActive);
}
