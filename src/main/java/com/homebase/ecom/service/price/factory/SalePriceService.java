package com.homebase.ecom.service.price.factory;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;
import com.homebase.ecom.service.PriceStateService;
import com.homebase.ecom.service.impl.BasePriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class SalePriceService implements PriceStrategyService {


    BasePriceCalculator basePriceCalculator;

    @Autowired
    public SalePriceService(PriceServiceFactory priceServiceFactory,BasePriceCalculator basePriceCalculator) {
       this.basePriceCalculator=basePriceCalculator;
        priceServiceFactory.registerService(this);
    }

    @Override
    public CartItem calculatePrice(Price price,CartItem cartItem) {
        if (cartItem == null) return null;
        cartItem = basePriceCalculator.calculateBasePrice(price,cartItem);// why can you calcualte here
        if (cartItem.isWasOnSale()) {
            int quantity = (cartItem.getQuantity() != null) ? cartItem.getQuantity() : 0;
            BigDecimal salePrice = (cartItem.getSalePrice() != null) ? cartItem.getSalePrice() : BigDecimal.ZERO;

            // Total = salePrice × quantity
            cartItem.setTotalAmount(salePrice.multiply(BigDecimal.valueOf(quantity)));
        }

        return cartItem;
    }

    @Override
    public boolean isApplicable(Price price) {
        return price != null && price.isOnSale();
    }


}