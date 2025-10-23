package com.homebase.ecom.service.price.factory;

import com.homebase.ecom.command.priceline.PriceLinePayload;
import com.homebase.ecom.domain.PriceLine;

public interface PriceLineServiceFactory {
    PriceLineStrategyService getFactory(String county);

    void registerService( PriceLineStrategyService priceStrategyService);
}
