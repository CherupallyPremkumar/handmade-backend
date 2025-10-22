package com.homebase.ecom.service;

import com.homebase.ecom.domain.Cart;
import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;

public interface PriceStateService {

    Price createPrice(Price priceRequest);

    Price processByPriceId(String id, String event, Object payload);

    Price retrieveByPriceId(String id);

    CartItem calculate(CartItem cartItem) ;
}
