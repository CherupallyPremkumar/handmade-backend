package com.homebase.ecom.service;

import com.homebase.ecom.domain.CartItem;

public interface TaxService {
    CartItem calculateTax(CartItem cartItem);
}
