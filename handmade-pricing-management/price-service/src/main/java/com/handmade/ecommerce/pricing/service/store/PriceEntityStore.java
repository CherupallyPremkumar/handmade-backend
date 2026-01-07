package com.handmade.ecommerce.pricing.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.pricing.entity.Price;
import com.handmade.ecommerce.pricing.entity.RegionalPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.pricing.configuration.dao.PriceRepository;
import java.util.Optional;
import java.time.LocalDateTime;

public class PriceEntityStore implements EntityStore<Price> {
	@Autowired
	private PriceRepository priceRepository;

	@Override
	public void store(Price entity) {
		priceRepository.save(entity);
	}

	@Override
	public Price retrieve(String id) {
		Optional<Price> entity = priceRepository.findById(id);
		if (entity.isPresent())
			return entity.get();
		throw new NotFoundException(1500, "Unable to find Price with ID " + id);
	}

	/**
	 * Get price by variant ID
	 * 
	 * @param variantId Product variant ID
	 * @return Price entity
	 */
	public Price getPriceByVariantId(String variantId) {
		Optional<Price> price = priceRepository.findByVariantId(variantId);
		if (price.isPresent()) {
			return price.get();
		}
		throw new NotFoundException(1501, "No price found for variant: " + variantId);
	}

	/**
	 * Get regional price for variant and region
	 * Checks effective dates and returns active price
	 * 
	 * @param variantId Product variant ID
	 * @param regionId  Region ID
	 * @return Regional price or null if not found
	 */
	public RegionalPrice getRegionalPrice(String variantId, String region) {
		Price price = getPriceByVariantId(variantId);
		LocalDateTime now = LocalDateTime.now();

		return price.getRegionalPrices().stream()
				.filter(rp -> rp.getRegion().equals(region))
				.filter(rp -> rp.getIsActive())
				.filter(rp -> isEffective(rp, now))
				.findFirst()
				.orElse(null);
	}

	/**
	 * Check if regional price is effective at given time
	 */
	private boolean isEffective(RegionalPrice rp, LocalDateTime now) {
		boolean afterStart = rp.getEffectiveFrom() == null || !now.isBefore(rp.getEffectiveFrom());
		boolean beforeEnd = rp.getEffectiveTo() == null || !now.isAfter(rp.getEffectiveTo());
		return afterStart && beforeEnd;
	}
}
