package com.handmade.ecommerce.promotion.pricing;

import com.handmade.ecommerce.promotion.pricing.entity.PriceHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PriceHistory
 * Generated from entity file
 */
@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, String> {
    
    List<PriceHistory> findByOfferId(String offerId);
    List<PriceHistory> findByProductId(String productId);
    List<PriceHistory> findBySellerId(String sellerId);
    List<PriceHistory> findByCurrencyCode(String currencyCode);
}
