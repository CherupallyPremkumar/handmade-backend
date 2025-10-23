package com.homebase.ecom.service.price.factory;

import com.homebase.ecom.command.priceline.PriceLinePayload;
import com.homebase.ecom.domain.PriceLine;
import org.chenile.base.exception.ServerException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PriceLineServiceFactoryImpl implements PriceLineServiceFactory {

    private final List<PriceLineStrategyService> strategies = new ArrayList<>();

    // Get the right strategy based on payload
    @Override
    public PriceLineStrategyService getFactory(String country) {
        for (PriceLineStrategyService strategy : strategies) {
            if (strategy.isApplicable(country)) { // now using payload
                return strategy;
            }
        }
        throw new ServerException(
                "No applicable price line strategy found for productVariantId: " + country
        );
    }

    @Override
    public void registerService(PriceLineStrategyService strategy) {
        strategies.add(strategy);
    }
}
