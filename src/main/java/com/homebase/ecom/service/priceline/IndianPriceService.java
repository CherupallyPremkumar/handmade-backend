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

    PriceLineEntityStore priceLineEntityStore;

    BasePriceLineCalculator basePriceCalculator;

    @Autowired
    @Qualifier("_priceLineStateEntityService_")
    PriceLineStateService priceLineStateService;


    @Autowired
    public IndianPriceService(PriceLineServiceFactory priceLineServiceFactory, BasePriceLineCalculator basePriceLineCalculator) {
       this.basePriceCalculator=basePriceCalculator;
        priceLineServiceFactory.registerService(this);
    }


    @Override
    public CartItem calculatePrice(String priceId, CartItem cartItem) {
        if (cartItem == null) return null;
        PriceLine priceLine= priceLineEntityStore.findByPriceIdAndCurrency(priceId,"USA");
        if (cartItem.isWasOnSale()) {
            int quantity = (cartItem.getQuantity() != null) ? cartItem.getQuantity() : 0;
            BigDecimal salePrice = (cartItem.getSalePrice() != null) ? cartItem.getSalePrice() : BigDecimal.ZERO;

            // Total = salePrice × quantity
            cartItem.setTotalAmount(salePrice.multiply(BigDecimal.valueOf(quantity)));
        }

        return cartItem;
    }

    @Override
    public boolean isApplicable(String country) {
       return  "IN".equalsIgnoreCase(country);
    }

    @Override
    public PriceLine create(PriceLinePayload priceLinePayload) {
        return null;
    }
}