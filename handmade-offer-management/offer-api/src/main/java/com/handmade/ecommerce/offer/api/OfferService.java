package com.handmade.ecommerce.offer.api;

import com.handmade.ecommerce.offer.dto.OfferDto;
import java.util.List;

/**
 * Service for managing Offers.
 * An Offer unifies Product Variant, Seller, Price, and Inventory.
 */
public interface OfferService {
    OfferDto createOffer(OfferDto offerDto);
    OfferDto updateOffer(String offerCode, OfferDto offerDto);
    OfferDto getOffer(String offerCode);
    OfferDto getOffer(String variantId, String sellerId, String regionId);
    List<OfferDto> getOffersForVariant(String variantId);
    List<OfferDto> getOffersBySeller(String sellerId);
}
