package com.handmade.ecommerce.promotion.pricing;

import com.handmade.ecommerce.promotion.pricing.entity.PriceAlert;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PriceAlert
 * Generated from entity file
 */
@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, String> {
    
    List<PriceAlert> findByCustomerId(String customerId);
    List<PriceAlert> findByProductId(String productId);
    List<PriceAlert> findByCurrencyCode(String currencyCode);
    List<PriceAlert> findByAlertType(String alertType);
    List<PriceAlert> findByStatus(String status);
}
