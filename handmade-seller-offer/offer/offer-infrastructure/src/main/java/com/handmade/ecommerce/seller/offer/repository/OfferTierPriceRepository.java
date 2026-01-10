package com.handmade.ecommerce.seller.offer;

import com.handmade.ecommerce.seller.offer.entity.OfferTierPrice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for OfferTierPrice
 * Generated from entity file
 */
@Repository
public interface OfferTierPriceRepository extends JpaRepository<OfferTierPrice, String> {
    
    List<OfferTierPrice> findByOfferId(String offerId);
}
