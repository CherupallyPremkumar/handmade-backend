package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;

public interface BasePriceCalculator {
    CartItem calculateBasePrice(Price price, CartItem cartItem);
}
