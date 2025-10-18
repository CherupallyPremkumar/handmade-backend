package com.homebase.ecom.service;

import com.homebase.ecom.domain.Cart;
import org.chenile.workflow.dto.StateEntityServiceResponse;

public interface CartStateService<Cart> {

    StateEntityServiceResponse<Cart> create(Cart order);

    StateEntityServiceResponse<Cart> processById(String id, String event, Object payload);

    StateEntityServiceResponse<Cart> retrieve(String id);
}
