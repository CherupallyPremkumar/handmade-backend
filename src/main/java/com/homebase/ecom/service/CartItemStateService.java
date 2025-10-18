package com.homebase.ecom.service;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.domain.CartItem;
import org.chenile.workflow.dto.StateEntityServiceResponse;

public interface CartItemStateService<CartItem> {
    StateEntityServiceResponse<CartItem> create(CartItem cart);

    StateEntityServiceResponse<CartItem> processById(String id, String event, Object payload);

    StateEntityServiceResponse<CartItem> retrieve(String id);
}
