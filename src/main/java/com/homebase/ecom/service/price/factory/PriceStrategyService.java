package com.homebase.ecom.service.price.factory;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;

import java.math.BigDecimal;

public interface PriceStrategyService {
     CartItem calculatePrice(Price price,CartItem cartItem);
     default boolean isApplicable(Price price) {
          return true; // By default, always applicable
     }
}
