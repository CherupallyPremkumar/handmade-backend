package com.homebase.ecom.service.price.factory;

import com.homebase.ecom.command.priceline.PriceLinePayload;
import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;
import com.homebase.ecom.domain.PriceLine;

import java.math.BigDecimal;

public interface PriceLineStrategyService {
     CartItem calculatePrice(String priceId,CartItem cartItem);

    boolean isApplicable(String country);
    PriceLine create( PriceLinePayload priceLinePayload);
}
