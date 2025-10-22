package com.homebase.ecom.service.price.factory;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;

import com.homebase.ecom.service.impl.BasePriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RegularPriceService implements PriceStrategyService {

    private final BasePriceCalculator basePriceCalculator;

    @Autowired
    public RegularPriceService(PriceServiceFactory priceServiceFactory, BasePriceCalculator basePriceCalculator) {
        this.basePriceCalculator = basePriceCalculator;
        priceServiceFactory.registerService(this);
    }

    @Override
    public CartItem calculatePrice(Price price, CartItem cartItem) {
        if (cartItem == null) return null;

        // Calculate base price first
        cartItem = basePriceCalculator.calculateBasePrice(price, cartItem);

        // Apply regular pricing logic
        int quantity = (cartItem.getQuantity() != null) ? cartItem.getQuantity() : 0;
        cartItem.setSalePrice(cartItem.getOriginalPrice()); // no discount
        cartItem.setTotalAmount(cartItem.getOriginalPrice().multiply(BigDecimal.valueOf(quantity)));
        cartItem.setWasOnSale(false);

        return cartItem;
    }

    @Override
    public boolean isApplicable(Price price) {
        return price != null && !price.isOnSale(); // Only apply if not on sale
    }
}