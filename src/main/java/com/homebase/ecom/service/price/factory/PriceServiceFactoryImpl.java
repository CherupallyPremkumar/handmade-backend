package com.homebase.ecom.service.price.factory;

import com.homebase.ecom.domain.Price;
import org.chenile.base.exception.ServerException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class PriceServiceFactoryImpl implements PriceServiceFactory{

    private static final List<PriceStrategyService> priceStrategyService = new ArrayList<>();
    @Override
    public PriceStrategyService getFactory(Price price) {
        for (PriceStrategyService s : priceStrategyService) {
            if (s.isApplicable(price)) {
                return s;
            }
        }
        throw new ServerException("No applicable price strategy found for product: " + price.getProductVariantId());
    }

    @Override
    public void registerService(PriceStrategyService priceStrategyService) {
          this.priceStrategyService.add(priceStrategyService);
    }
}
