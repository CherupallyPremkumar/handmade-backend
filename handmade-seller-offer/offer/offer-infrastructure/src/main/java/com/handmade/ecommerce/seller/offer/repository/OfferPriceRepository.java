package com.handmade.ecommerce.seller.offer;

import com.handmade.ecommerce.seller.offer.entity.OfferPrice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for OfferPrice
 * Generated from entity file
 */
@Repository
public interface OfferPriceRepository extends JpaRepository<OfferPrice, String> {
    
    List<OfferPrice> findByOfferId(String offerId);
    List<OfferPrice> findByCurrencyCode(String currencyCode);
    List<OfferPrice> findByPriceType(String priceType);
}
