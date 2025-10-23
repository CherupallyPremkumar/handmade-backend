package com.homebase.ecom.service.priceline;

import com.homebase.ecom.command.priceline.PriceLinePayload;
import com.homebase.ecom.domain.CartItem;

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
public class USRetailPriceStrategy implements PriceLineStrategyService {

    private final BasePriceLineCalculator basePriceCalculator;

    PriceLineEntityStore priceLineEntityStore;

    @Autowired
            @Qualifier("_priceLineStateEntityService_")
    PriceLineStateService priceLineStateService;
    @Autowired
    public USRetailPriceStrategy(PriceLineServiceFactory priceLineServiceFactory, BasePriceLineCalculator basePriceCalculator) {
        this.basePriceCalculator = basePriceCalculator;
        priceLineServiceFactory.registerService(this);
    }

    @Override
    public CartItem calculatePrice(String priceId, CartItem cartItem) {
        if (cartItem == null) return null;
       PriceLine priceLine= priceLineEntityStore.findByPriceIdAndCurrency(priceId,"USA");
        // Calculate base price first
        cartItem = basePriceCalculator.calculateBasePrice(priceLine, cartItem);
        // Apply regular pricing logic
        int quantity = (cartItem.getQuantity() != null) ? cartItem.getQuantity() : 0;
        cartItem.setSalePrice(cartItem.getOriginalPrice()); // no discount
        cartItem.setTotalAmount(cartItem.getOriginalPrice().multiply(BigDecimal.valueOf(quantity)));
        cartItem.setWasOnSale(false);

        return cartItem;
    }


    @Override
    public boolean isApplicable(String country) {
        return "US".equalsIgnoreCase(country);
    }

    @Override
    public PriceLine create( PriceLinePayload priceLinePayload) {
        return priceLineStateService.createPriceLine(priceLinePayload);
    }
}