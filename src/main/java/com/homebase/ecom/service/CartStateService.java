package com.homebase.ecom.service;

import com.homebase.ecom.domain.Cart;
import org.chenile.workflow.dto.StateEntityServiceResponse;

public interface CartStateService {

    public void refreshCart(String cartId);


}
