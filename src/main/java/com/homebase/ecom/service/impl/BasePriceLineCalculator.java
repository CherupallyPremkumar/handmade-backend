package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;
import com.homebase.ecom.domain.PriceLine;

public interface BasePriceLineCalculator {
    CartItem calculateBasePrice(PriceLine priceline, CartItem cartItem);
}
