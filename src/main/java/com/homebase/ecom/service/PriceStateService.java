package com.homebase.ecom.service;

import com.homebase.ecom.command.priceline.PriceLinePayload;
import com.homebase.ecom.domain.Cart;
import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.Price;

public interface PriceStateService {

    CartItem calculate(CartItem cartItem) ;
}
