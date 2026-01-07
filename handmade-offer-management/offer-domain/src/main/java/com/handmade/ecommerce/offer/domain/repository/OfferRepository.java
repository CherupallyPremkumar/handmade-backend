package com.handmade.ecommerce.offer.domain.repository;

import com.handmade.ecommerce.offer.domain.aggregate.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {
    Optional<Offer> findByOfferCode(String offerCode);
    List<Offer> findByVariantId(String variantId);
    List<Offer> findBySellerId(String sellerId);
    Optional<Offer> findByVariantIdAndSellerIdAndRegionId(String variantId, String sellerId, String regionId);
}
