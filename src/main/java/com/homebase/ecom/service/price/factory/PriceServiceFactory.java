package com.homebase.ecom.service.price.factory;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;

import java.math.BigDecimal;

public interface PriceServiceFactory {
    PriceStrategyService getFactory(Price price);

    void registerService( PriceStrategyService priceStrategyService);
}
