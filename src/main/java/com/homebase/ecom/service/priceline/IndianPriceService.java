package com.homebase.ecom.service.priceline;

import com.homebase.ecom.command.priceline.PriceLinePayload;
import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;
import com.homebase.ecom.domain.PriceLine;
import com.homebase.ecom.entitystore.PriceLineEntityStore;
import com.homebase.ecom.service.PriceLineStateService;
import com.homebase.ecom.service.impl.BasePriceLineCalculator;
import com.homebase.ecom.service.price.factory.PriceLineServiceFactory;
import com.homebase.ecom.service.price.factory.PriceLineStrategyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class IndianPriceService implements PriceLineStrategyService {

    private static final String COUNTRY_CODE = "IN";
    private static final String CURRENCY = "INR";

    private final PriceLineEntityStore priceLineEntityStore;
    private final BasePriceLineCalculator basePriceCalculator;
    private final PriceLineStateService priceLineStateService;

    @Autowired
    public IndianPriceService(
            PriceLineServiceFactory priceLineServiceFactory, 
            BasePriceLineCalculator basePriceLineCalculator,
            PriceLineEntityStore priceLineEntityStore,
            @Qualifier("_priceLineStateEntityService_") PriceLineStateService priceLineStateService) {
        this.basePriceCalculator = basePriceLineCalculator;
        this.priceLineEntityStore = priceLineEntityStore;
        this.priceLineStateService = priceLineStateService;
        priceLineServiceFactory.registerService(this);
    }

    @Override
    public CartItem calculatePrice(String priceId, CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        PriceLine priceLine = priceLineEntityStore.findByPriceIdAndCurrency(priceId, CURRENCY);
        if (priceLine == null) {
            throw new IllegalStateException(
                "No price found for priceId: " + priceId + " and currency: " + CURRENCY
            );
        }
        return basePriceCalculator.calculateBasePrice(priceLine, cartItem);
    }

    @Override
    public boolean isApplicable(String country) {
        return COUNTRY_CODE.equalsIgnoreCase(country);
    }

    @Override
    public PriceLine create(PriceLinePayload priceLinePayload) {
        if (priceLinePayload == null) {
            throw new IllegalArgumentException("PriceLinePayload cannot be null");
        }
        
        // Create PriceLine with Indian-specific settings
        PriceLine priceLine = new PriceLine();
        priceLine.setPriceId(priceLinePayload.getPriceId());
        priceLine.setCurrency(CURRENCY);
        priceLine.setRegion(COUNTRY_CODE);
        priceLine.setAmount(priceLinePayload.getAmount());  // Use getAmount() not getBaseAmount()
        priceLine.setSaleAmount(priceLinePayload.getSaleAmount());
        priceLine.setDiscountPercentage(priceLinePayload.getDiscountPercentage());
        priceLine.setSaleStartDate(priceLinePayload.getSaleStartDate());
        priceLine.setSaleEndDate(priceLinePayload.getSaleEndDate());
        priceLine.setPriceType(priceLinePayload.getPriceType());
        priceLine.setTaxCategory(priceLinePayload.getTaxCategory());
        
        // TODO: Integrate with state service when available
        // For now, return the created priceLine
        return priceLine;
    }
}